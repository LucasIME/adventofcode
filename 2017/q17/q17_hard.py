def process(step):
    s = [0]
    cur_i = 0
    len_s = 1
    resp = None
    for data in range(1, 50000001):
        cur_i = (cur_i + step) % len_s
        cur_i += 1
        len_s += 1
    return resp

def main():
    entry = int(input())
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()
