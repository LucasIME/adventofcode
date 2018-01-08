def encode(s):
    resp = ""
    for letter in s:
        if letter == '\\' or letter == '"':
            resp += '\\' + letter
        else:
            resp += letter
    return '"' + resp + '"'

def process(entry_list):
    diff_list = [len(encode(entry)) - len(entry) for entry in entry_list]
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
