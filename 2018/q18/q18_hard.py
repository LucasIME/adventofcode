from collections import defaultdict
import sys

def parse_input():
    matrix = []
    for line in sys.stdin:
        matrix.append(list(line.strip()))
    return matrix

OPEN = "."
TREE = "|"
LUMBERYARD = "#"

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

def v_freq(v):
    freqs = defaultdict(int)
    for x in v:
        freqs[x] += 1
    return freqs

def evolve_cell(row, col, grid):
    cur_cell = grid[row][col]
    neighs = get_neighs(row, col, grid)
    neigh_freqs = v_freq(neighs)

    if cur_cell == OPEN:
        if neigh_freqs[TREE] >= 3:
            return TREE
        return OPEN

    if cur_cell == TREE:
        if neigh_freqs[LUMBERYARD] >= 3:
            return LUMBERYARD
        return TREE

    if cur_cell == LUMBERYARD:
        if neigh_freqs[LUMBERYARD] >= 1 and neigh_freqs[TREE] >= 1:
            return LUMBERYARD
        return OPEN

    raise Exception("should never get here")

def evolve_grid(grid):
    return [[evolve_cell(row_i, col_i, grid) for col_i, col in enumerate(row)] for row_i, row in enumerate(grid)]

def count_types(grid):
    type_freq = {}
    for row in grid:
        for cell in row:
            if cell not in type_freq:
                type_freq[cell] = 0
            type_freq[cell] += 1
    return type_freq

def hashable(grid):
    return '\n'.join([''.join(row) for row in grid])


def process(original_grid):
    time = 1000000000
    seen = set()

    i = 0
    grid = original_grid
    seen_on_index = {}
    while hashable(grid) not in seen:
        seen.add(hashable(grid))
        seen_on_index[hashable(grid)] = i 
        grid = evolve_grid(grid)
        i += 1

    initial_offset = seen_on_index[hashable(grid)]
    repeat_freq = i - initial_offset

    needed_reps = (time - initial_offset) % repeat_freq
    for i in range(needed_reps):
        grid = evolve_grid(grid)
    
    frequencies = count_types(grid)

    return frequencies[TREE] * frequencies[LUMBERYARD]

def main():
    entry = parse_input()
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()
