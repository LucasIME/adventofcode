from collections import defaultdict

def parse_line(line):
    target = line[0]
    op1 = line[1]
    inc_v = int(line[2])
    if_var = line[4]
    if_op = line[5]
    if_val = int(line[6])
    return target, op1, inc_v, if_var, if_op, if_val

def process_line(line, d):
    target, op1, inc_v, if_var, if_op, if_val = parse_line(line)
    if eval("{0} {1} {2}".format(d[if_var], if_op, if_val)):
        if op1 == 'inc':
            d[target] += inc_v
        else:
            d[target] -= inc_v

def get_largest(entry):
    d = defaultdict(int)
    resp = float('-inf')
    for line in entry:
        process_line(line, d)
        resp = max(resp, d[max(d, key=d.get)])
    return resp

def main():
    entry = []
    while True:
        line = input()
        if line == '':
            break
        entry.append(line.split())
    resp = get_largest(entry)
    print(resp)

if __name__ == '__main__':
    main()
