import re
from collections import defaultdict
import heapq

pattern = re.compile('(.*-)*(\d+)\[(.+)\]')

def decrypt_letter(letter, id):
    if letter == '-':
        return ' '
    id = (id + ord(letter) - ord('a'))%26
    return chr(ord('a') + id)

def decrypt(entry):
    match = pattern.match(entry).group(1)
    resp = []
    id = get_id(entry)
    for letter in match:
        resp.append(decrypt_letter(letter, id))
    return ''.join(resp)

def get_name_string(entry):
    match = pattern.match(entry).group(1)
    return ''.join(match.split('-'))

def is_real(entry):
    checksum = get_checksum(entry)
    freq = defaultdict(int)
    name = get_name_string(entry)
    h = []
    for letter in name:
        freq[letter] += 1
    for letter, value in freq.items():
        heapq.heappush(h, (-value, letter))
    expected_checksum = []
    for i in range(5):
        expected_checksum.append(heapq.heappop(h)[1])
    return ''.join(expected_checksum) == checksum

def get_checksum(entry):
    return pattern.match(entry).group(3)

def get_id(entry):
    return int(pattern.match(entry).group(2))

def decode_real_rooms(entry_list):
    for entry in entry_list:
        if is_real(entry):
            print(entry, decrypt(entry))

def main():
    entry_list = []
    while True:
        entry = input()
        if entry == '':
            break
        entry_list.append(entry)
    decode_real_rooms(entry_list)

if __name__ == '__main__':
    main()
