def get_min(r, c):
    return int(abs(r) + abs(c))

def process_move(move, r, c):
    if move == 'n':
        return r-1, c
    elif move == 's':
        return r+1, c
    elif move == 'ne':
        return r-0.5, c+0.5
    elif move == 'nw':
        return r-0.5, c-0.5
    elif move == 'se':
        return r+0.5, c+0.5
    elif move == 'sw':
        return r+0.5, c-0.5

def get_dist(entry):
    r = 0
    c = 0
    max_d = 0
    for move in entry:
        r, c = process_move(move, r, c)
        max_d = max(max_d, get_min(r, c))
    return max_d

def main():
    entry = input()
    entry = entry.split(',')
    resp = get_dist(entry)
    print(resp)

if __name__ == '__main__':
    main()
