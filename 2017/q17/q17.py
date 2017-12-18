def process(step):
    s = [0]
    cur_i = 0
    for data in range(1, 2018):
        cur_i = (cur_i + step) % len(s)
        cur_i += 1
        s.insert(cur_i, data)
    return s[cur_i + 1]

def main():
    entry = int(input())
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()
