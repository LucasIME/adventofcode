def parse_unit(unit):
    return unit.split()

def parse(entry_list):
    return list(map(parse_unit, entry_list))

def rotate(base, steps):
    if steps < 0:
        steps = steps + len(base)
    first = base[0:len(base) - steps]
    second = base[len(base) - steps:]

    return second + first

def swap_pos(password, x, y):
    t_string = list(password)
    t_string[x], t_string[y] = t_string[y], t_string[x]
    return ''.join(t_string)

def swap_letter(password, c1, c2):
    x, y = password.find(c1), password.find(c2)
    t_string = list(password)
    for i, c in enumerate(t_string):
        if c == c1:
            t_string[i] = c2
        elif c == c2:
            t_string[i] = c1
    return ''.join(t_string)

def move(password, x, y):
    t_string = list(password)
    c = t_string[x]
    del t_string[x]
    t_string.insert(y, c)
    return ''.join(t_string)

# assumes operation is uniquely reversible for that length
def gen_reverse_table_for_len(length):
    rev_table = {}
    for i in range(length):
        steps = i + 1
        steps = steps + 1 if i >=4 else steps
        final = (i + steps) % length
        rev_table[final] = i
    return rev_table

def exec_reversed(command, password):
    name = command[0]
    if name == 'swap':
        target = command[1]
        if target == 'position':
            x, y = int(command[2]), int(command[-1])
            return swap_pos(password, x, y)

        elif target == 'letter':
            c1, c2 = command[2], command[-1]
            return swap_letter(password, c1, c2)

    elif name == 'rotate':
        target = command[1]
        if target == 'left':
            steps = -int(command[2])
            steps = -steps
            return rotate(password, steps)

        elif target == 'right':
            steps = int(command[2])
            steps = -steps
            return rotate(password, steps)

        elif target == 'based':
            letter = command[-1]
            letter_index = password.find(letter)
            rev_table = gen_reverse_table_for_len(len(password))
            orig_index = rev_table[letter_index]
            return rotate(password, orig_index - letter_index)

    elif name == 'reverse':
        x, y = int(command[2]), int(command[-1])
        return password[:x] + password[x:y+1][::-1] + password[y+1:]

    elif name == 'move':
        x, y = int(command[2]), int(command[-1])
        return move(password, y, x)

    return password

def process(entry_list):
    password = 'fbgdceah'
    commands = parse(entry_list)
    commands.reverse()

    for command in commands:
        password = exec_reversed(command, password)

    return password

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
