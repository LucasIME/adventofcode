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
    if cmd_name == 'set':
        d[arg1] = arg2
    elif cmd_name == 'sub':
        d[arg1] -= arg2
    elif cmd_name == 'jnz':
        x = get_val(arg1, d)
        if x != 0:
            return i + arg2
    return i+1

def get_recovery(entry):
    i = 0
    d = defaultdict(int)
    for c in range(8):
        d[chr(ord('a') + c)] = 0
    resp = 0
    while i < len(entry):
        command = entry[i]
        if command[0] == 'mul':
            resp += 1
        i = process_command(command, d, i)
    return resp

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
