def is_valid(passphrase):
    used = set()
    for word in passphrase:
        sorted_word = ''.join(sorted(word))
        if sorted_word in used:
            return False
        used.add(sorted_word)
    return True

def count_passphrases(entry_list):
    total = 0
    for passphrase in entry_list:
        if is_valid(passphrase):
            total += 1
    return total

def main():
    matrix = []
    while True:
        line = input()
        if line == '':
            break
        line = line.split()
        matrix.append(line)
    resp = count_passphrases(matrix)
    print(resp)

if __name__ == '__main__':
    main()
