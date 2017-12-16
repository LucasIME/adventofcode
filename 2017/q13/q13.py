def update_state(s_state, scan_max, dir):
    for key, v in s_state.items():
        if dir[key] == 1:
            if s_state[key] == scan_max[key] - 1:
                s_state[key] -= 1
                dir[key] = -1
            else:
                s_state[key] += 1
        elif dir[key] == -1:
            if s_state[key] == 0:
                s_state[key] += 1
                dir[key] = 1
            else:
                s_state[key] -= 1

def get_severity(entry):
    scan_max = {}
    width = 0
    for line in entry:
        col, size = [int(x) for x in line.split(': ')]
        scan_max[col] = size
        width = col
    s_state = {col: 0 for col in scan_max}
    dir = {col: 1 for col in scan_max}
    cur_i = -1
    severity = 0
    for second in range(width+1):
        cur_i += 1
        if cur_i in scan_max and s_state[cur_i] == 0:
            severity += cur_i * scan_max[cur_i]
        update_state(s_state, scan_max, dir)
    return severity

def main():
    entry = []
    while True:
        line = input()
        if line == '':
            break
        entry.append(line)
    resp = get_severity(entry)
    print(resp)

if __name__ == '__main__':
    main()
