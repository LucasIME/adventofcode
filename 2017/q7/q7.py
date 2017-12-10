def parse_right(item):
    return item.split(',')[0]

def get_root(entry_list):
    total = []
    is_right_rule = set()
    for entry in entry_list:
        total.append(entry[0])
        if len(entry) > 3:
            for i, item in enumerate(entry[3:]):
                is_right_rule.add(parse_right(item))
    for item in total:
        if item not in is_right_rule:
            return item
    return 0 

def main():
    entry = []
    while True:
        line = input()
        if line == '':
            break
        entry.append(line.split())
    resp = get_root(entry)
    print(resp)

if __name__ == '__main__':
    main()
