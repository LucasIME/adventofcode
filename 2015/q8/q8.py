def real_len(s):
    size = 0
    i = 1
    while i < len(s) - 1:
        letter = s[i]
        if letter == '\\':
            next_letter = s[i+1]
            if next_letter == '\\' or next_letter == '"':
                size += 1
                i += 2
            elif next_letter == 'x':
                i += 4
                size += 1
        else:
            i += 1
            size += 1
    return size

def process(entry_list):
    diff_list = [len(entry) - real_len(entry) for entry in entry_list]
    return sum(diff_list)

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
