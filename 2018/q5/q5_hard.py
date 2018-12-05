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

def get_letters(entry):
    resp = set()
    for letter in entry:
        if letter.islower():
            resp.add(letter)
    return resp

def get_final_react_len(entry):
    last = entry
    # cannot use recursive solution because the call stack is too big
    while True:
        entry = react(last)
        if entry == last:
            break
        last = entry
    return len(react(entry))


def process(entry):
    letters_set = get_letters(entry)
    mini = float('inf')
    for letter in letters_set:
        lowerletter = letter
        upperletter = letter.upper()
        changed_entry = entry.replace(letter, '').replace(upperletter, '')
        react_len = get_final_react_len(changed_entry)
        mini = min(react_len, mini)
    return mini

def main():
    entry = input()
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()

