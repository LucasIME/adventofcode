def get_neigh(x, y):
    resp = []
    for i1 in range(-1, 2):
        for i2 in range(-1, 2):
            if i1 != 0 or i2 != 0:
                resp.append((x+i1, y+i2))
    return resp

def update_pos(x, y):
    cur_lvl = max(abs(x), abs(y))
    if y == -cur_lvl:
        return (x+1, y)
    if x == cur_lvl and y != cur_lvl:
        return (x, y+1)
    if y == cur_lvl and x != -cur_lvl:
        return (x-1, y)
    if x == -cur_lvl and y != -cur_lvl:
        return (x, y-1)

def get_spiral_larger_than(n):
    pad = {(0, 0): 1}
    x = 0
    y = 0
    val = 1
    while val <= n:
        x, y = update_pos(x, y)
        neighs = get_neigh(x, y)
        new_val = 0
        for pos in neighs:
            if (pos[0], pos[1]) in pad:
                new_val += pad[(pos[0], pos[1])]
        pad[(x, y)] = new_val
        val = new_val
    return val 

def main():
    n = int(input())
    resp = get_spiral_larger_than(n)
    print(resp)

if __name__ == '__main__':
    main()

