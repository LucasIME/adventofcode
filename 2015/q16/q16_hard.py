def parse(entry_list):
    v = []
    for entry in entry_list:
        sue_obj = {}
        props = ''.join(entry.split(':')[1:]).split(',')
        for prop_str in props:
            name, val = prop_str.split()
            sue_obj[name] = int(val)
        v.append(sue_obj)
    return v

def process_entry(entry_list):
    sue_list = parse(entry_list)
    target = {
        'children': 3,
        'cats': 7,
        'samoyeds': 2,
        'pomeranians': 3,
        'akitas': 0,
        'vizslas': 0,
        'goldfish': 5,
        'trees': 3,
        'cars': 2,
        'perfumes': 1
    }

    for i, sue in enumerate(sue_list):
        is_right = True
        for target_prop, target_val in target.items():
            if target_prop == 'cats' or target_prop == 'trees':
                if target_prop in sue and sue[target_prop] <= target_val:
                    is_right = False
                    break
            elif target_prop == 'pomeranians' or target_prop == 'goldfish':
                if target_prop in sue and sue[target_prop] >= target_val:
                    is_right = False
                    break
            elif target_prop in sue and sue[target_prop] != target_val:
                is_right = False
                break

        if is_right:
            return i+1, sue
    return None

def main():
    entry_list = []
    while True:
        entry = input()
        if entry == '':
            break
        entry_list.append(entry)
    resp = process_entry(entry_list)
    print(resp)

if __name__ == '__main__':
    main()
