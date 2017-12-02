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

def get_distance(entry):
    print(entry)
    x = 0
    y = 0
    orientation = ORIENTATION["N"]
    for direction, number in entry:
        orientation = (orientation + DIRECTION[direction])%4
        if orientation == ORIENTATION['N']:
            y += number
        elif orientation == ORIENTATION['E']:
            x += number
        elif orientation == ORIENTATION['S']:
            y -= number
        elif orientation == ORIENTATION['W']:
            x -= number
    return abs(x) + abs(y)

def main():
    entry = input()
    entry = clean_input(entry)
    resp = get_distance(entry)
    print(resp)
if __name__ == '__main__':
    main()
