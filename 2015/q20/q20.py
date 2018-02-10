def process(entry):
    out_vec = [0 for i in range(1000000)]
    for i in range(1, len(out_vec)):
        cur = i
        while cur < len(out_vec):
            out_vec[cur] += 10*i
            cur += i

    for i, item in enumerate(out_vec):
        if item >= entry:
            return i, item

def main():
    entry = int(input())
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()
