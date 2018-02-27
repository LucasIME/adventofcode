from collections import defaultdict

def try_get_val(key, registers):
    try:
        return int(key)
    except:
        return registers[key]

def process_command(command, registers):
    op = command[0]
    arg1 = command[1]
    if len(command) == 3:
        arg2 = command[2]

    if op == 'cpy':
        registers[arg2] = try_get_val(arg1, registers)
    elif op == 'jnz':
        if try_get_val(arg1, registers) != 0:
            return int(arg2)
    elif op == 'inc':
        registers[arg1] += 1
    elif op == 'dec':
        registers[arg1] -= 1
    return 1


def process(entry_list):
    registers = defaultdict(int)
    registers['c'] = 1
    i = 0
    while i < len(entry_list):
        command = entry_list[i]
        i += process_command(command, registers)
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
