def spin(n, s, anti_s):
    for key, val in s.items():
        s[key] = (val + n)%len(s)
    for char, i in s.items():
        anti_s[i] = char
    return s, anti_s

def exchange(pos1, pos2, s, anti_s):
    anti_s[pos1], anti_s[pos2] = anti_s[pos2], anti_s[pos1] 
    s = {c:i for i, c in enumerate(anti_s)}
    return s, anti_s

def partner(c1, c2, s, anti_s):
    s[c1], s[c2] = s[c2], s[c1]
    for char, i in s.items():
        anti_s[i] = char
    return s, anti_s

def process_command(command, s, anti_s):
    if command[0] == 's':
        arg = int(command[1:])
        return spin(arg, s, anti_s)
    elif command[0] == 'x':
        pos1, pos2 = [int(x) for x in command[1:].split('/')]
        return exchange(pos1, pos2, s, anti_s)
    elif command[0] == 'p':
        c1, c2 = [c for c in command[1:].split('/')]
        return partner(c1, c2, s, anti_s)

def process(entry):
    s = {chr(ord('a') + i):i for i in range(16)}
    anti_s = [chr(ord('a') + i) for i in range(16)]
    seen = []
    for i in range(1000000000):
        s_string = ''.join(anti_s)
        if s_string in seen:
            # we found the loop size
            return seen[1000000000 % len(seen)]
        else:
            seen.append(s_string)
            for command in entry:
                s, anti_s = process_command(command, s, anti_s)
    return ''.join(anti_s)

def main():
    entry = input()
    entry = entry.split(',')
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()
