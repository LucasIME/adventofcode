from collections import defaultdict
from itertools import permutations

WALL = '#'
FREE = '.'

def get_number_pos(maze):
    positions = {}
    for rown, row in enumerate(maze):
        for col, symbol in enumerate(row):
            if symbol != WALL and symbol != FREE:
                positions[symbol] = (rown, col)
    return positions

def is_valid(maze, row, col):
    if row < 0 or row >= len(maze):
        return False
    if col < 0 or col >= len(maze[row]):
        return False
    return maze[row][col] != WALL

def get_neigh(maze, row, col):
    resp = []
    for r, c in [(row-1, col), (row+1, col), (row, col-1), (row, col+1)]:
            if is_valid(maze, r, c):
                resp.append((r, c))
    return resp

def get_dist_to_other_nodes(maze, row, col):
    dists = defaultdict(lambda: float('inf'))
    #row, col, dist
    queue = [(row, col, 0)]
    visited = set()
    while queue:
        r, c, dist = queue.pop()
        if (r,c) in visited:
            continue
        symbol = maze[r][c]
        if symbol != WALL and symbol != FREE:
            dists[symbol] = min(dist, dists[symbol])
        visited.add((r,c))
        for neigh in get_neigh(maze, r, c):
            queue.insert(0, (neigh[0], neigh[1], dist+1))
    return dists


def get_min_path(dists_from_node):
    nodes = dists_from_node.keys()
    maxi = max([int(x) for x in nodes])
    nodes = [str(x) for x in range(1, maxi+1)]
    shortest_path = float('inf')
    for permutation in permutations(nodes, len(nodes)):
        path = 0
        past_node = '0'
        for letter in list(permutation) + ['0']:
            path += dists_from_node[past_node][letter]
            past_node = letter
        shortest_path = min(shortest_path, path)
    return shortest_path

def process(maze):
    number_pos = get_number_pos(maze)
    dists_from_node = {}
    for node, position in number_pos.items():
        r, c = position
        dists_from_node[node] = get_dist_to_other_nodes(maze, r, c)
    return get_min_path(dists_from_node)


def main():
    entry_list = []
    while True:
        entry = input()
        if entry == '':
            break
        entry_list.append(list(entry))
    resp = process(entry_list)
    print(resp)

if __name__ == '__main__':
    main()
