def get_garbage_len(entry, start, end):
    total = 0
    i = start + 1
    while i < end:
        item = entry[i]
        if item == '!':
            i += 1
        else:
            total += 1
        i += 1
    return total

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
    while i < len(entry):
        item = entry[i]
        if item == '<':
            garbage_end = find_garbage_match(entry, i)
            total += get_garbage_len(entry, i, garbage_end)
            i = garbage_end
        elif item == '!':
            i += 1
        i += 1
    return total

def main():
    entry = input()
    resp = get_score(entry)
    print(resp)

if __name__ == '__main__':
    main()
