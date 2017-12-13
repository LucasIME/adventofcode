suffix = [17, 31, 73, 47, 23]

def get_hex(hash):
    resp = [] 
    for n in hash:
        hex_string = hex(n)
        if len(hex_string) == 3:
            resp.append('0' + hex_string[-1])
        elif len(hex_string) == 4:
            resp.append(hex_string[2:])
    return ''.join(resp)

def get_dense_hash(sparse_hash):
    resp = []
    for block in range(16):
        cur = 0
        for index in range(16):
            cur ^= sparse_hash[16*block + index]
        resp.append(cur)
    return resp

def parse_input(input):
    resp = []
    for char in input:
        resp.append(ord(char))
    return resp + suffix

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

def get_hash(entry):
    v = [x for x in range(256)]
    cur_i = 0
    skip = 0
    for round in range(64):
        cur_i, skip = process_round(v, entry, cur_i, skip)
    dense_hash = get_dense_hash(v)
    return get_hex(dense_hash)

def main():
    entry = input()
    entry = parse_input(entry)
    resp = get_hash(entry)
    print(resp)

if __name__ == '__main__':
    main()
