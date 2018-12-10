def process_node(entry, start):
    nChild = entry[start]
    nMeta = entry[start + 1]
    total = 0

    if nChild == 0:
        for i in range(nMeta):
            total += entry[start + 2 + i]
        return total, start + 2 + nMeta

    nextChildStart = start+2
    for i in range(nChild):
        cur_sum, nextChildStart = process_node(entry, nextChildStart)
        total += cur_sum
    for i in range(nMeta):
        total += entry[nextChildStart + i]
    nextChildStart = nextChildStart + nMeta
    return total, nextChildStart


def process(entry_list):
    i = 0
    total = 0
    while i < len(entry_list):
        n, index = process_node(entry_list, i)
        total += n
        i = index
    return total

def main():
    line = input()
    entry_list = [int(x) for x in line.split(' ')]
    resp = process(entry_list)
    print(resp)

if __name__ == '__main__':
    main()

