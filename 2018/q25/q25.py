import sys

def parse_input():
    points = []
    for line in sys.stdin:
        nums = [int(x) for x in  line.strip().split(",")]
        points.append(nums)
    return points 

def is_valid(row, col, grid):
    return row >= 0 and col >= 0 and row < len(grid) and col < len(grid[row])

def get_neighs(row, col, grid):
    resp = []
    for r in range(row-1, row+2):
        for c in range(col-1, col+2):
            if r == row and c == col:
                continue
            if not is_valid(r, c, grid):
                continue
            resp.append(grid[r][c])
    return resp

def dist(p1, p2):
    d = 0
    for i in range(len(p1)):
        d += abs(p2[i] - p1[i])
    return d

def belongs_to_set(set, point):
    for p2 in set:
        if dist(point, p2) <= 3:
            return True
    return False

def intersect(set1, set2):
    for p1 in set1:
        if belongs_to_set(set2, p1):
            return True
    return False
    
def collapse(sets):
    resp = []
    for set in sets:
        has_matched = False
        for i, set2 in enumerate(resp):
            if intersect(set, set2):
                resp[i] = set + set2
                has_matched = True
                break
        if not has_matched:
            resp.append(set)
    return resp

def process(points):
    sets = []
    for point in points:
        has_matched = False
        for target_set in sets:
            if belongs_to_set(target_set, point):
                target_set.append(point)
                has_matched = True
                break
        if not has_matched:
            sets.append([point])

    while True:
        new_sets = collapse(sets)
        if new_sets == sets:
            break
        sets = new_sets

    return len(sets)

def main():
    entry = parse_input()
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()
