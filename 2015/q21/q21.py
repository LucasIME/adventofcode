from math import ceil

def parse(entry_list):
    d = {}
    for line in entry_list:
        stat, val = line.split(': ')
        d[stat] = int(val)
    return d

def get_rounds_for_player(player_dmg, player_def, boss):
    return ceil(boss['Hit Points']/max(1, player_dmg - boss['Armor']))

def get_rounds_for_boss(player_dmg, player_def, boss):
    return ceil(100/max(1, boss['Damage'] - player_def))

def will_player_lose(player_dmg, player_def, boss):
    return get_rounds_for_player(player_dmg, player_def, boss) > get_rounds_for_boss(player_dmg, player_def, boss)

def find_max_lose(boss):
    weaps = {
        "Dagger": [8, 4, 0],
        "Shortsword": [10, 5, 0],
        "Warhammer":   [25, 6, 0],
        "Longsword":   [40, 7, 0],
        "Greataxe" :  [74, 8, 0],
    }
    armours = {
        "Leather":   [13, 0, 1],
        "Chainmail":   [31, 0, 2],
        "Splintmail":   [53, 0, 3],
        "Bandedmail":   [75, 0, 4],
        "Platemail":  [102, 0, 5],
        "NoneArmor" :  [0, 0, 0],
    }

    rings = {
        "Damage +1":  [25, 1, 0],
        "Damage +2":  [50, 2, 0],
        "Damage +3": [100, 3, 0],
        "Defense +1":  [20, 0, 1],
        "Defense +2":  [40, 0, 2],
        "Defense +3":  [80, 0, 3],
        "NoneRing1" :  [0, 0, 0],
        "NoneRing2" :  [0, 0, 0],
    }

    min_price = float('inf')
    for weap_name, weap in weaps.items():
        for armor_name, armor in armours.items():
            for ring1_name, ring1 in rings.items():
                for ring2_name, ring2 in rings.items():
                    if ring2_name == ring1_name:
                        continue
                    cur_dmg = weap[1] + armor[1] + ring1[1] + ring2[1]
                    cur_def = weap[2] + armor[2] + ring1[2] + ring2[2]
                    cur_price = weap[0] + armor[0] + ring1[0] + ring2[0]
                    if not will_player_lose(cur_dmg, cur_def, boss):
                        min_price = min(min_price, cur_price)
    return min_price

def process(entry_list):
    boss = parse(entry_list)
    return find_max_lose(boss)

def main():
    entry_list = []
    while True:
        entry = input()
        if entry == '':
            break
        entry_list.append(entry)
    resp = process(entry_list)
    print(resp)

if __name__ == '__main__':
    main()
