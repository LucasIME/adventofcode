def parse(entry_list):
    v = [0 for i in range(len(entry_list))]
    for index, entry in enumerate(entry_list):
        s = entry.split()
        i, n = int(s[-1][:-1]), int(s[3])
        v[index] = (i, n)
    return v

def ball_passes(v, time):
    for i, val in enumerate(v):
        size = val[1]
        cur = (val[0] + time + i + 1)%size
        if cur != 0:
            return False
    return True

def get_time(v):
    time = 0
    while True:
        if ball_passes(v, time):
            return time
        time += 1
    return time

def process(entry_list):
    v = parse(entry_list)
    return get_time(v)

def main():
    entry_list = []
    while True:
        entry = input()
        if entry == '':
            break
        entry_list.append(entry)
    resp = process(entry_list)
    print(resp)

if __name__ == '__main__':
    main()
