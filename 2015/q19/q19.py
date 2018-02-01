from collections import defaultdict

def parse(entry_list):
    d = defaultdict(list)
    for entry in entry_list:
        e1, e2 = entry.split(' => ')
        d[e1].append(e2)
    return d

def expand(word, rules, s, res):
    for start in range(len(word)):
        for end in range(start+1, len(word)):
            if word[start:end] in rules:
                for transform in rules[word[start:end]]:
                    s.add(word[:start] + transform + word[end:])
    return

def process(entry_list, passw):
    rules = parse(entry_list)
    s = set()
    expand(passw, rules, s, '')
    return len(s)

def main():
    entry_list = []
    count = 0
    passw = None
    while True:
        entry = input()
        if entry == '':
            count += 1
            if count == 2:
                break
        elif count == 1:
            passw = entry
        if count != 1:
            entry_list.append(entry)
    resp = process(entry_list, passw)
    print(resp)

if __name__ == '__main__':
    main()
