suffix = [17, 31, 73, 47, 23]

def parse_input(input):
    resp = []
    for char in input:
        resp.append(ord(char))
    return resp + suffix

def get_dense_hash(sparse_hash):
    resp = []
    for block in range(16):
        cur = 0
        for index in range(16):
            cur ^= sparse_hash[16*block + index]
        resp.append(cur)
    return resp

def knot_hash(entry):
    entry = parse_input(entry)
    v = [x for x in range(256)]
    cur_i = 0
    skip = 0
    for round in range(64):
        cur_i, skip = process_round(v, entry, cur_i, skip)
    dense_hash = get_dense_hash(v)
    return dense_hash

def reverse(v, start, end):
    v[start:end] = v[start:end][::-1]

def process_length(v, length, cur_i):
    if cur_i + length < len(v):
        reverse(v, cur_i, cur_i + length)
    else:
        end = (cur_i + length) % len(v)
        temp = v[cur_i:] + v[:end]
        reverse(temp, 0, len(temp))
        for index in range(length):
            v[(cur_i + index)%len(v)] = temp[index]

def process_round(v, entry, cur_i, skip):
    for length in entry:
        process_length(v, length, cur_i)
        cur_i = (cur_i + length + skip) % len(v)
        skip += 1
    return cur_i, skip

def count_one(matrix):
    resp = 0
    for line in matrix:
        for item in line:
            if item == 1:
                resp += 1
    return resp

def to_bin(hash):
    resp = []
    for n in hash:
        bin_s = bin(n).split('b')[1] 
        resp += [0 for i in range(8 - len(bin_s))] + [int(digit) for digit in bin_s]
    return resp

def get_squares(entry):
    grid = [[0 for i in range(128)] for j in range(128)]
    for i in range(128):
        cur_entry = entry + '-' + str(i)
        hash = knot_hash(cur_entry)
        grid[i] = to_bin(hash)
    return count_one(grid)

def main():
    entry = input()
    resp = knot_hash(entry)
    resp = get_squares(entry)
    print(resp)

if __name__ == '__main__':
    main()
