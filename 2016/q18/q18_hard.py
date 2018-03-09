TRAP = '^'
SAFE = '.'

def count_safe(row):
    resp = 0
    for c in row:
        if c == SAFE:
            resp += 1
    return resp

def is_trap(base_row, i):
    if i == -1 or i >= len(base_row):
        return False
    left = base_row[i-1] == TRAP if i >= 1 else False
    mid = base_row[i] == TRAP
    right = base_row[i+1] == TRAP if i < len(base_row) -1 else False

    return ((left and mid and (not right)) or
            (mid and right and (not left)) or
            (left and (not mid) and (not right)) or
            (right and (not mid) and (not left))
           )

def get_next_row(row):
    new_row = [0 for c in row]
    for i in range(len(row)):
        new_row[i] = TRAP if is_trap(row, i) else '.'
    return new_row

def process(entry):
    row = list(entry)
    resp = 0
    for i in range(400000):
        resp += count_safe(row)
        row = get_next_row(row)
    return resp

def main():
    entry = input()
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()
