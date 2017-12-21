from collections import defaultdict

def get_val(name, d):
    if name in d:
        return d[name]
    try:
        return int(name)
    except:
        return name

def process_command(cmd, d, i, my_q, other_q):
    cmd_name = cmd[0]
    arg1 = cmd[1]
    arg2 = get_val(cmd[-1], d)
    if cmd_name == 'snd':
        other_q.insert(0, get_val(arg1, d))
    elif cmd_name == 'rcv':
        if my_q:
            d[arg1] = my_q.pop()
        else:
            return i, True
    elif cmd_name == 'set':
        d[arg1] = arg2
    elif cmd_name == 'add':
        d[arg1] += arg2
    elif cmd_name == 'mul':
        d[arg1] *= arg2
    elif cmd_name == 'mod':
        d[arg1] %= arg2
    elif cmd_name == 'jgz':
        if get_val(arg1, d) > 0:
            return i + arg2, False
    return i+1, False

def get_snd_count_1(entry):
    i1, i2 = 0, 0
    d1 = defaultdict(int)
    d1['p'] = 0
    d2 = defaultdict(int)
    d2['p'] = 1
    q1, q2 = [], []
    wait1, wait2 = False, False
    resp = 0
    while not wait1 or not wait2:
        command1, command2 = entry[i1], entry[i2]
        if command2[0] == 'snd':
            resp += 1
        i1, wait1 = process_command(command1, d1, i1, q1, q2)
        i2, wait2 = process_command(command2, d2, i2, q2, q1)
    return resp

def main():
    entry = []
    while True:
        line = input()
        if line == '':
            break
        line = line.split()
        entry.append(line)
    resp = get_snd_count_1(entry)
    print(resp)

if __name__ == '__main__':
    main()
