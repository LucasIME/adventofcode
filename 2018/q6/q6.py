from collections import defaultdict

def dist(x, y, x2, y2):
    return abs(x-x2) + abs(y - y2)

def get_closest_point(x, y, entry_list):
    closest = None
    miniDist = float('inf')
    for i, (px, py) in enumerate(entry_list):
        distance = dist(x, y, px, py)
        if distance < miniDist:
           closest = (px, py)
           miniDist = distance
        elif distance == miniDist:
            closest = None
    return closest

def process(entry_list):
    CONST_DIFF = 10000
    mini_x = min([entry[0] for entry in entry_list]) - CONST_DIFF//len(entry_list) - 1
    max_x = max([entry[0] for entry in entry_list]) + CONST_DIFF//len(entry_list) + 1
    mini_y = min([entry[1] for entry in entry_list]) - CONST_DIFF//len(entry_list) - 1
    max_y = max([entry[1] for entry in entry_list]) + CONST_DIFF//len(entry_list) + 1
    length = len(entry_list)

    mapping = {}

    for x in range(mini_x, max_x + 1):
        for y in range(mini_y, max_y + 1):
            closest = get_closest_point(x, y, entry_list)
            mapping[(x, y)] = closest

    rev_mapping = defaultdict(int)
    for point in mapping:
        if not mapping[point]:
            continue
        # if a point is in one of the corners, we can discard it
        if point[0] in (mini_x, max_x) or point[1] in (mini_y, max_y):
            rev_mapping[mapping[point]] = float('-inf')
        rev_mapping[mapping[point]] += 1

    return max(rev_mapping.values())

def main():
    entry_list = []
    while True:
       line = input()
       if line == '':
           break
       entry_list.append([int(x) for x in line.split(', ')])
    resp = process(entry_list)
    print(resp)

if __name__ == '__main__':
    main()

