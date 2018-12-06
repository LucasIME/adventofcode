def dist(x, y, x2, y2):
    return abs(x-x2) + abs(y - y2)

def get_dist_sum(x, y, entry_list):
    resp = 0
    for px, py in entry_list:
        resp += dist(x, y, px, py)
    return resp

def process(entry_list):
    TARGET_DIST = 10000
    mini_x = min([entry[0] for entry in entry_list]) - TARGET_DIST//len(entry_list) - 1
    max_x = max([entry[0] for entry in entry_list]) + TARGET_DIST//len(entry_list) + 1
    mini_y = min([entry[1] for entry in entry_list]) - TARGET_DIST//len(entry_list) - 1
    max_y = max([entry[1] for entry in entry_list]) + TARGET_DIST//len(entry_list) + 1
    length = len(entry_list)

    area_size = 0

    for x in range(mini_x, max_x + 1):
        for y in range(mini_y, max_y + 1):
            dist_sum = get_dist_sum(x, y, entry_list)
            if dist_sum < TARGET_DIST:
                area_size += 1

    return area_size

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

