def parse_unit(unit):
    start, end = [int(x) for x in unit.split('-')]
    return (start, end)

def parse(entry_list):
    return list(map(parse_unit, entry_list))

def merge_range(pairs):
    resp = [pairs[0]]
    for pair in pairs[1:]:
        start, end = pair
        resp_start, resp_end = resp[-1]

        if start > resp_end:
            resp.append(pair)

        elif start >= resp_start and start <= resp_end:
            resp[-1] = (resp_start, max(end, resp_end))

    return resp

def process(entry_list):
    full_ip_count = 4294967295 + 1
    pairs = parse(entry_list)
    pairs.sort()
    pairs = merge_range(pairs)

    resp = full_ip_count

    for range in pairs:
        start, end = range
        resp -= (end - start) + 1

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
