from collections import defaultdict

def parse(args, registers):
    if len(args) == 1:
        try:
            return int(args[0]), None
        except:
            return args[0], None
        pass
    elif len(args) == 2:
        return args[0][:-1], int(args[1])

def process_instruction(instruction, args, registers):
    arg1, arg2 = parse(args, registers)
    if instruction == 'hlf':
        registers[arg1] //= 2
    elif instruction == 'tpl':
        registers[arg1] *= 3
    elif instruction == 'inc':
        registers[arg1] += 1
    elif instruction == 'jmp':
        return arg1
    elif instruction == 'jie':
        return arg2 if registers[arg1] % 2 == 0 else 1
    elif instruction == 'jio':
        return arg2 if registers[arg1] == 1 else 1
    return 1

def process(entry_list):
    registers = defaultdict(int)
    i = 0
    while i < len(entry_list) and i >= 0:
        raw_instruction = entry_list[i]
        instruction, args = raw_instruction.split()[0], raw_instruction.split()[1:]
        offset = process_instruction(instruction, args, registers)
        i += offset
    return registers['b']

def main():
    entry_list = []
    while True:
        entry = input()
        if entry == '':
            break
        entry_list.append(entry)
    resp = process(entry_list)
    print(resp)

if __name__ == '__main__':
    main()
