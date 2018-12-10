from collections import defaultdict, deque

MUL_CHANGER = 23
LEFT_CONST = 7

def parse(entry):
    split = entry.split(' ')
    return int(split[0]), int(split[-2])

def get_player(marble, players):
    base = marble%players
    if base == 0:
        return players
    return base

def process(entry):
    players, points = parse(entry)
    curMarbleIndex = 0
    circle = deque([0])
    player_points = defaultdict(int)
    points *= 100
    for marble in range(1, points + 1):
        player = get_player(marble, players)
        if marble % MUL_CHANGER == 0:
            circle.rotate(LEFT_CONST)
            player_points[player] += marble
            player_points[player] += circle.pop()
            circle.rotate(-1)
        else:
            circle.rotate(-1)
            circle.append(marble)
    return max(player_points.values())

def main():
    entry = input()
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()

