OPEN = '.'
WALL = '#'

def build_table(rows, cols, seed):
    m = [ [0 for col in range(cols)] for row in range(rows)]
    for col in range(cols):
        for row in range(rows):
            if is_open(col, row, seed):
                m[row][col] = OPEN
            else:
                m[row][col] = WALL
    return m

def count_ones(n):
    total = 0
    while n > 0:
        total += n & 1
        n >>= 1
    return total

def is_open(x, y, seed):
    base = x*x + 3*x + 2*x*y + y + y*y
    base += seed
    ones = count_ones(base)
    return True if ones % 2 == 0 else False

def is_valid(row, col):
    return row >=0 and col >= 0

def get_neighs(row, col, graph):
    resp = []
    for i in range(-1, 2):
        if i != 0:
            if is_valid(row + i, col) and graph[row+i][col] == OPEN:
                    resp.append((row + i, col))
            if is_valid(row, col+i) and graph[row][col+i] == OPEN:
                    resp.append((row, col + i))
    return resp

def get_max_dist(graph, steps):
    q = [(1, 1, steps)]
    visited = set()
    while q:
        cur_row, cur_col, steps_left = q.pop()
        visited.add((cur_row, cur_col))

        if steps_left == 0:
            continue

        for neigh in get_neighs(cur_row, cur_col, graph):
            if neigh not in visited:
                q.insert(0, (neigh[0], neigh[1], steps_left - 1))

    return len(visited)

def process(entry):
    seed = entry
    graph = build_table(150, 150, seed)
    return get_max_dist(graph, 50)

def main():
    entry = int(input())
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()
