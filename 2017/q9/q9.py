def find_garbage_match(entry, i):
    while i < len(entry):
        item = entry[i]
        if item == '!':
            i += 1
        elif item == '>':
            return i
        i += 1

def get_score(entry):
    i = 0
    total = 0
    cur_g = 0
    while i < len(entry):
        item = entry[i]
        if item == '<':
            i = find_garbage_match(entry, i)
        elif item == '!':
            i += 1
        elif item == '{':
            cur_g += 1
        elif item == '}':
            total += cur_g
            cur_g -= 1
        i += 1
    return total

def main():
    entry = input()
    resp = get_score(entry)
    print(resp)

if __name__ == '__main__':
    main()
