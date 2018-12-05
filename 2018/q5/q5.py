def match(a, b):
    if a.islower() and a.upper() == b:
        return True
    if a.isupper() and a.lower() == b:
        return True
    return False

def react(entry):
    for i, letter in enumerate(entry):
        if i == 0:
            continue
        last = entry[i-1]
        if match(letter, last):
            return entry[:i-1] + entry[i+1:]
    return entry

def process(entry):
    last = entry
    # cannot use recursive solution because the call stack is too big
    while True:
        entry = react(last)
        if entry == last:
            break
        last = entry
    return len(react(entry))

def main():
    entry = input()
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()

