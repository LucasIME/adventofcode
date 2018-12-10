from collections import defaultdict

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

def get_next_marble_pos(circle, curMarbleIndex):
    length = len(circle)
    nextPost = (curMarbleIndex+1)%length
    next2 = nextPost+1
    return next2

def get_max_val(d):
    resp = float('-inf')
    for key, v in d.items():
        resp = max(resp, v)
    return resp

def process(entry):
    players, points = parse(entry)
    curMarbleIndex = 0
    circle = [0]
    player_points = defaultdict(int)
    for marble in range(1, points + 1):
        player = get_player(marble, players)
        if marble % MUL_CHANGER == 0:
            player_points[player] += marble
            to_be_removed = (curMarbleIndex - LEFT_CONST)%len(circle)
            player_points[player] += circle[to_be_removed]
            del circle[to_be_removed]
            curMarbleIndex = to_be_removed
        else:
            next_pos = get_next_marble_pos(circle, curMarbleIndex)
            if next_pos >= len(circle):
                circle.append(marble)
            else:
                circle.insert(next_pos, marble)
            curMarbleIndex = next_pos
    return get_max_val(player_points)

def main():
    entry = input()
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()

