from collections import defaultdict

def isint(x):
    try:
        t = int(x)
        return True
    except:
        return False

def toggle(entry_list, index):
    if index >= len(entry_list):
        return

    command = entry_list[index]
    op = command[0]

    if len(command) == 3:
        if op == 'jnz':
            entry_list[index][0] = 'cpy'
        else:
            entry_list[index][0] = 'jnz'
    elif len(command) == 2:
        if op == 'inc':
            entry_list[index][0] = 'dec'
        else:
            entry_list[index][0] = 'inc'


def try_get_val(key, registers):
    try:
        return int(key)
    except:
        return registers[key]

def process_command(command, registers, entry_list, cur_i):
    op = command[0]
    arg1 = command[1]
    if len(command) == 3:
        arg2 = command[2]

    if op == 'cpy':
        if isint(arg2) or len(command) == 2:
           return 1
        registers[arg2] = try_get_val(arg1, registers)
    elif op == 'jnz':
        if len(command) == 2: #if (not isint(arg2)) or len(command) == 2:
           return 1
        if try_get_val(arg1, registers) != 0:
            return try_get_val(arg2, registers) #int(arg2)
    elif op == 'tgl':
        if len(command) == 3:
           return 1
        toggle(entry_list, cur_i + try_get_val(arg1, registers))
        return 1
    elif op == 'inc':
        if isint(arg1) or len(command) == 3:
           return 1
        registers[arg1] += 1
    elif op == 'dec':
        if isint(arg1) or len(command) == 3:
           return 1
        registers[arg1] -= 1
    return 1


def process(entry_list):
    registers = defaultdict(int)
    registers['a'] = 7
    i = 0
    while i < len(entry_list):
        command = entry_list[i]
        i += process_command(command, registers, entry_list, i)
    return registers["a"]

def main():
    entry_list = []
    while True:
        entry = input()
        if entry == '':
            break
        entry_list.append(entry.split())
    resp = process(entry_list)
    print(resp)

if __name__ == '__main__':
    main()
