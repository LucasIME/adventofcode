def get_max(v):
    maxtot = float('-inf')
    maxi = -1
    for i, item in enumerate(v):
        if item > maxtot:
            maxtot = item
            maxi = i
    return maxtot, maxi

def hash(entry):
    return ' '.join([str(x) for x in entry])

def count_cycle(entry):
    visited = set() 
    total = 0
    cur = entry
    while hash(cur) not in visited:
        visited.add(hash(cur))
        max, maxi = get_max(cur)
        cur[maxi] = 0
        maxi = (maxi + 1) % len(cur)
        while max > 0:
            cur[maxi] += 1
            max -= 1
            maxi = (maxi + 1) % len(cur)
        total += 1
    return total

def main():
    entry = input()
    entry = [int(x) for x in entry.split()]
    resp = count_cycle(entry)
    print(resp)

if __name__ == '__main__':
    main()
