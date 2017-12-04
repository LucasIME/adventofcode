import re

pattern = re.compile('\[|\]')

def is_abba(s):
    if len(s) != 4:
        return False
    return (s[0] != s[1]) and (s[0] == s[3]) and (s[1] == s[2])

def has_abba_pattern(s):
    for i in range(0, len(s)-3):
        if is_abba(s[i:i+4]):
            return True
    return False

def supports_tls(entry):
    split = re.split(pattern, entry)
    outside = split[::2]
    inside = split[1::2]

    outside_has_abba = False
    inside_has_abba = False

    for word in outside:
        if has_abba_pattern(word):
            outside_has_abba = True
            break
    for word in inside:
        if has_abba_pattern(word):
            inside_has_abba = True
            break

    return outside_has_abba and not inside_has_abba

def count_tls(entry_list):
    total = 0
    for entry in entry_list:
        if supports_tls(entry):
            total += 1
    return total

def main():
    entry_list = []
    while True:
        entry = input()
        if entry == '':
            break
        entry_list.append(entry)
    resp = count_tls(entry_list)
    print(resp)

if __name__ == '__main__':
    main()
