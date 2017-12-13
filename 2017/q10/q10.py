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

def get_mul(entry):
    v = [x for x in range(256)]
    cur_i = 0
    skip = 0
    for length in entry:
        process_length(v, length, cur_i)
        cur_i = (cur_i + length + skip) % len(v)
        skip += 1
    return v[0] * v[1]

def main():
    entry = input()
    entry = [int(x) for x in entry.split(',')]
    resp = get_mul(entry)
    print(resp)

if __name__ == '__main__':
    main()
