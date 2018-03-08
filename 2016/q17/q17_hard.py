from hashlib import md5

OPEN = set(['b', 'c', 'd', 'e', 'f'])
DIR = {
    0: 'U',
    1: 'D',
    2: 'L',
    3: 'R',
}

def is_valid(row, col, dir):
    if row == 0 and dir == 0:
        return False
    if row == 3 and dir == 1:
        return False
    if col == 0 and dir == 2:
        return False
    if col == 3 and dir == 3:
        return False
    return True

def get_next_state(row, col, dir):
    if dir == 'U':
        return row-1, col
    elif dir == 'D':
        return row+1, col
    elif dir == 'L':
        return row, col-1
    elif dir == 'R':
        return row, col+1

def base_str(entry, step_list):
    return entry + ''.join(step_list)

def get_path(row, col, entry, step_list, memo):
    if row == 3 and col == 3:
        return step_list

    code = md5(base_str(entry, step_list).encode('utf-8')).hexdigest()

    possibilities = {}
    for i in [1, 3, 2, 0]:
        if code[i] in OPEN and is_valid(row, col, i):
            next_row, next_col = get_next_state(row, col, DIR[i])
            possibilities[DIR[i]] = get_path(next_row, next_col, entry, step_list + [DIR[i]], memo)

    if possibilities == {}:
        return None

    maxi = float('-inf')
    resp = None
    for dir, path in possibilities.items():
        if path is None:
            continue
        if len(path) > maxi:
            resp = path
            maxi = len(path)

    return resp

def process(entry):
    cur_row, cur_col = 0, 0
    return len(get_path(cur_row, cur_col, entry, [], {}))

def main():
    entry = input()
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()
