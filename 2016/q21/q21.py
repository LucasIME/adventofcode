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

def exec_command(command, password):
    name = command[0]
    if name == 'swap':
        target = command[1]
        if target == 'position':
            x, y = int(command[2]), int(command[-1])
            t_string = list(password)
            t_string[x], t_string[y] = t_string[y], t_string[x]
            return ''.join(t_string)

        elif target == 'letter':
            c1, c2 = command[2], command[-1]
            x, y = password.find(c1), password.find(c2)
            t_string = list(password)
            t_string[x], t_string[y] = t_string[y], t_string[x]
            return ''.join(t_string)

    elif name == 'rotate':
        target = command[1]
        if target == 'left':
            steps = -int(command[2])
            return rotate(password, steps)

        elif target == 'right':
            steps = int(command[2])
            return rotate(password, steps)

        elif target == 'based':
            letter = command[-1]
            letter_index = password.find(letter)
            steps = 1 + letter_index
            if letter_index >= 4:
                steps += 1
            return rotate(password, steps)

    elif name == 'reverse':
        x, y = int(command[2]), int(command[-1])
        return password[:x] + password[x:y+1][::-1] + password[y+1:]

    elif name == 'move':
        x, y = int(command[2]), int(command[-1])
        t_string = list(password)
        c = t_string[x]
        del t_string[x]
        t_string.insert(y, c)
        return ''.join(t_string)

    return password

def process(entry_list):
    password = 'abcdefgh'
    commands = parse(entry_list)

    for command in commands:
        password = exec_command(command, password)

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
