from collections import defaultdict

def get_val(name, d):
    if name in d:
        return d[name]
    try:
        return int(name)
    except:
        return name

def process_command(cmd, d, i):
    cmd_name = cmd[0]
    arg1 = cmd[1]
    arg2 = get_val(cmd[-1], d)
    if cmd_name == 'snd':
        d['played'] = get_val(arg1, d)
    elif cmd_name == 'rcv':
        if d[arg1] != 0:
            if 'played' in d:
                d['rcv'] = d['played']
    elif cmd_name == 'set':
        d[arg1] = arg2
    elif cmd_name == 'add':
        d[arg1] += arg2
    elif cmd_name == 'mul':
        d[arg1] *= arg2
    elif cmd_name == 'mod':
        d[arg1] %= arg2
    elif cmd_name == 'jgz':
        if d[arg1] > 0:
            return i + arg2
    return i+1

def get_recovery(entry):
    i = 0
    d = defaultdict(int)
    while i < len(entry):
        command = entry[i]
        i = process_command(command, d, i)
        if 'rcv' in d:
            return d['rcv']

def main():
    entry = []
    while True:
        line = input()
        if line == '':
            break
        line = line.split()
        entry.append(line)
    resp = get_recovery(entry)
    print(resp)

if __name__ == '__main__':
    main()
