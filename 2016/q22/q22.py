def parse_row(row):
    splits = row.split()
    x = int(splits[0].split('-')[1].split('x')[1])
    y = int(splits[0].split('-')[2].split('y')[1])
    used = int(splits[2].split('T')[0])
    free = int(splits[3].split('T')[0])
    return {'x': x, 'y': y, 'used': used, 'free': free}

def parse(entry_list):
    return [parse_row(row) for row in entry_list[2:]]

def fits(entry1, entry2):
    if entry1['used'] == 0:
        return False

    return entry1['used'] <= entry2['free']

def process(entry_list):
    parsed = parse(entry_list)

    resp = 0

    for i, entry1 in enumerate(parsed):
        for entry2 in parsed[i+1:]:
            if fits(entry1, entry2):
                resp += 1
            if fits(entry2, entry1):
                resp += 1

    return resp

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
