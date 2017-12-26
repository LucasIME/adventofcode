DIR = {
    'LEFT': 0,
    'UP': 1,
    'RIGHT': 2,
    'DOWN': 3
}

STATUS = {
    '.': 0,
    'W': 1,
    '#': 2,
    'F': 3
}

def build_map(entry):
    map = {}
    for y, line in enumerate(entry):
        for x, item in enumerate(line):
            map[(x, y)] = STATUS[item]
    return map

def move(x, y, dir):
    if dir == DIR['UP']:
        return x, y-1
    elif dir == DIR['RIGHT']:
        return x+1, y
    elif dir == DIR['DOWN']:
        return x, y+1
    elif dir == DIR['LEFT']:
        return x-1, y

def burst(x, y, dir, map):
    state = get_state(x, y, map)
    if state == STATUS['.']:
        dir = (dir - 1)%4
    elif state == STATUS['#']:
        dir = (dir + 1)%4
    elif state == STATUS['F']:
        dir = (dir + 2)%4

    map[(x, y)] = (state+1)%4

    x, y = move(x, y, dir)

    return x, y, dir

def get_state(x, y, map):
    if (x, y) in map:
        return map[(x, y)]
    return 0

def count_burts(entry, count):
    map = build_map(entry)
    dir = DIR['UP']
    x = len(entry)//2
    y = len(entry)//2
    resp = 0
    for i in range(count):
        old = get_state(x, y, map)
        last_x, last_y = x, y
        x, y, dir = burst(x, y, dir, map)
        new = get_state(last_x, last_y, map)
        if new == STATUS['#'] and old != STATUS['#']:
            resp += 1
    return resp

def main():
    entry = []
    while True:
        line = input()
        if line == '':
            break
        entry.append(line)
    resp = count_burts(entry, 10000000)
    print(resp)

if __name__ == '__main__':
    main()
