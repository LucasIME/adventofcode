def process(entry_list):
    visited = set([0])
    cur = 0
    while True:
        for n in entry_list:
            cur += n
            if cur in visited:
                return cur
            visited.add(cur)

def main():
    entry_list = []
    while True:
       line = input()
       if line == '':
           break
       entry_list.append(int(line))
    resp = process(entry_list)
    print(resp)


if __name__ == '__main__':
    main()

