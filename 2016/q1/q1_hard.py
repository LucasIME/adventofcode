def clean_input(entry):
    entry = entry.split(', ')
    entry = [(x[0], int(x[1:])) for x in entry]
    return entry

DIRECTION = {
    "L": -1,
    "R": 1
}

ORIENTATION = {
    "N": 0,
    "E": 1,
    "S": 2,
    "W": 3
}

def get_next_orientation(orientation, direction):
    orientation = (orientation + DIRECTION[direction])%4
    return orientation

def get_distance(entry):
    print(entry)
    x = 0
    y = 0
    orientation = ORIENTATION['N']
    visited = set()
    for direction, number in entry:
        orientation = get_next_orientation(orientation, direction)
        for i in range(number):
            if orientation == ORIENTATION['N']:
                y += 1
            if orientation == ORIENTATION['E']:
                x += 1
            if orientation == ORIENTATION['W']:
                x -= 1
            if orientation == ORIENTATION['S']:
                y -= 1

            if (x, y) in visited:
                return abs(x) + abs(y)
            visited.add((x, y))

    return abs(x) + abs(y)

def main():
    entry = input()
    entry = clean_input(entry)
    resp = get_distance(entry)
    print(resp)

if __name__ == '__main__':
    main()
