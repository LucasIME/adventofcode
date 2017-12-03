def find_level(n):
    i = 1
    while (2*i-1)**2 < n:
        i += 1
    return i

def get_spiral_dist(n):
    lvl = find_level(n)
    upper = (2*lvl -1)**2
    lower = upper - (2*lvl -2)

    while n < lower:
        lower -= (2*lvl -2)
        upper -= (2*lvl -2)

    mid = int((lower + upper)/2)
    resp = (lvl - 1) + abs(n - mid) 
    return resp

def main():
    n = int(input())
    resp = get_spiral_dist(n)
    print(resp)

if __name__ == '__main__':
    main()

