import re

pattern = re.compile('\[|\]')

def is_aba(s):
    if len(s) != 3:
        return False
    return (s[0] != s[1]) and (s[0] == s[2])

def find_aba_patterns(s):
    resp = set()
    for i in range(0, len(s)-2):
        if is_aba(s[i:i+3]):
            resp.add(s[i:i+3])
    return resp

def counter_pattern(s):
    return s[1] + s[0] + s[1]

def get_partition_aba_set(partition):
    partition_aba = set()
    for word in partition:
        abas = find_aba_patterns(word)
        partition_aba = partition_aba | abas

    return partition_aba

def supports_ssl(entry):
    split = re.split(pattern, entry)
    outside = split[::2]
    inside = split[1::2]

    outside_aba = get_partition_aba_set(outside)
    inside_aba = get_partition_aba_set(inside)

    for aba_pattern in outside_aba:
        if counter_pattern(aba_pattern) in inside_aba:
            return True

    return False

def count_tls(entry_list):
    total = 0
    for entry in entry_list:
        if supports_ssl(entry):
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
