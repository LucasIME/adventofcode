from collections import defaultdict

def contains_letter_x(word, n):
    count = defaultdict(int)
    for letter in word:
        count[letter] += 1
    for letter, val in count.items():
        if val == n:
            return True
    return False

def process(entry_list):
    count2 = 0
    count3 = 0
    for entry in entry_list:
        if contains_letter_x(entry, 2):
            count2 += 1
        if contains_letter_x(entry, 3):
            count3 += 1
    return count2 * count3

def main():
    entry_list = []
    while True:
       line = input()
       if line == '':
           break
       entry_list.append(line)
    resp = process(entry_list)
    print(resp)

if __name__ == '__main__':
    main()

