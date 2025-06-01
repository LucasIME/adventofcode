from dataclasses import dataclass
from copy import deepcopy
import heapq

@dataclass
class Entity:
    hp: int
    mana: int
    dmg: int
    armor: int = 0

@dataclass
class Spell:
    id: int
    mana_cost: int
    dmg: int = 0
    hp: int = 0
    mana: int = 0
    armor: int = 0
    duration: int = 1

MAGIC_MISSILE = Spell(id = 0, mana_cost=53, dmg=4)
DRAIN = Spell(id=1, mana_cost=73, dmg=2, hp=2)
SHIELD = Spell(id=2, mana_cost=113, armor=7, duration=6)
POISON = Spell(id=3, mana_cost=173, dmg=3, duration=6)
RECHARGE = Spell(id=4, mana_cost=229, mana=101, duration=5)
ALL_SPELLS = [MAGIC_MISSILE, DRAIN, SHIELD, POISON, RECHARGE]

@dataclass
class State:
    player: Entity
    boss: Entity
    mana_spent: int = 0
    turn: int = 0
    active_spells: dict[int, Spell] = None

    def __lt__(self, other: 'State') -> bool:
        return self.mana_spent < other.mana_spent

    def __post_init__(self):
        if self.active_spells is None:
            self.active_spells = {}

    def is_boss_dead(self) -> bool:
        return self.boss.hp <= 0

    def is_player_dead(self) -> bool:
        return self.player.hp <= 0

    def play_boss_turn(self) -> 'State':
        state = self.apply_active_spells()
        state.player.hp -= max(1, state.boss.dmg - state.player.armor)
        return state

    def apply_active_spells(self) -> 'State':
        new_state = deepcopy(self)
        new_spell_states = {}

        for spell_id, spell in new_state.active_spells.items():
            new_duration = spell.duration - 1

            new_state.boss.hp -= spell.dmg
            new_state.player.mana += spell.mana

            if new_duration >= 1:
                new_spell_states[spell_id] = Spell(
                    id=spell.id,
                    mana_cost=spell.mana_cost,
                    dmg=spell.dmg,
                    hp=spell.hp,
                    mana=spell.mana,
                    armor=spell.armor,
                    duration=new_duration
                )
            else:
                # Spell has expired
                new_state.player.armor -= spell.armor

        new_state.active_spells = new_spell_states
        return new_state

    def play_spell(self, spell: Spell) -> 'State':
        new_state = deepcopy(self)
        new_state.mana_spent += spell.mana_cost
        new_state.player.mana -= spell.mana_cost

        if spell.duration <= 1:
            new_state.player.hp += spell.hp
            new_state.boss.hp -= spell.dmg

            return new_state

        new_state.player.armor += spell.armor
        new_state.active_spells[spell.id] = spell
        return new_state


def play(initial_state: State, hard=False):
    queue = [initial_state]

    while queue:
        cur_state = heapq.heappop(queue)

        if cur_state.is_boss_dead():
            return cur_state

        if hard:
            cur_state.player.hp -= 1
            if cur_state.is_player_dead():
                continue

        cur_state = cur_state.apply_active_spells()

        if cur_state.is_boss_dead():
            return cur_state

        if cur_state.is_player_dead():
            continue

        for spell in ALL_SPELLS:
            if spell.mana_cost > cur_state.player.mana:
                continue

            if spell.id in cur_state.active_spells:
                continue

            new_state = cur_state.play_spell(spell)
            new_state = new_state.play_boss_turn()
            heapq.heappush(queue, new_state)

    return None


def process(file_name, player_hp=50, player_mana=500):
    f = open(file_name, 'r')
    lines = f.readlines()
    boss_hp = int(lines[0].split(': ')[1])
    boss_dmg = int(lines[1].split(': ')[1])

    player = Entity(hp=player_hp, mana=player_mana, dmg=0)
    boss = Entity(hp=boss_hp, mana=0, dmg=boss_dmg)

    initial_state = State(player=player, boss=boss)
    state = play(initial_state, hard=True)

    return state.mana_spent

def main():
    processed = process('input.txt')
    print(processed)

if __name__ == '__main__':
    main()
