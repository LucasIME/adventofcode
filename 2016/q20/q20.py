def parse_unit(unit):
    start, end = [int(x) for x in unit.split('-')]
    return (start, end)

def parse(entry_list):
    return list(map(parse_unit, entry_list))

def process(entry_list):
    pairs = parse(entry_list)
    pairs.sort()
    mini = 0
    for pair in pairs:
        start, end = pair
        if mini >= start and mini <= end:
            mini = end + 1

    return mini

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
