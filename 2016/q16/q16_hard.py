DISK_LEN = 35651584

def invert(c):
    if c == '0':
        return '1'
    elif c == '1':
        return '0'

def dragon_curve(a):
    b = a[::-1]
    new_b = ''.join(list(map(invert, b)))
    return a + '0' + new_b

def gen_data(entry):
    while len(entry) < DISK_LEN:
        entry = dragon_curve(entry)
    return entry

def checksum(data):
    resp = []
    for i in range(0, len(data)-1, 2):
        a, b = data[i], data[i+1]
        if a == b:
            resp.append('1')
        else:
            resp.append('0')

    resp = ''.join(resp)
    if len(resp)%2 == 0:
        return checksum(resp)

    return resp


def process(entry):
    data = gen_data(entry)
    return checksum(data[:DISK_LEN])

def main():
    entry = input()
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()
