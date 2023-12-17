import sys
import heapq
from collections import defaultdict
import itertools

def parse_input(entry):
  return [ [int(c) for c in line_str] for line_str in entry]

def get_next_possible_dirs(dir):
  if dir == "left" or dir == "right":
    return ["up", "down"]
  if dir == "up" or dir == "down":
    return ["left", "right"]

  raise("Unknown dir" + dir)

def get_next(row, col, dir):
  if dir == "right":
    return (row, col +1)
  if dir == "left":
    return (row, col -1)
  if dir == "up":
    return (row -1 , col)
  if dir == "down":
    return (row +1 , col)

  raise("Unknown dir" + dir)

def is_valid(grid, row, col):
  return row >=0 and row < len(grid) and col >= 0 and col < len(grid[0])

def get_dist_after_steps(grid, row, col, cur_dir, cur_dist):
  resp = []

  for i in range(10):
    (next_row, next_col) = get_next(row, col, cur_dir)

    if not is_valid(grid, next_row, next_col):
      return resp

    new_dist = cur_dist + grid[next_row][next_col]

    if i >= 3: 
      resp.append((new_dist, (next_row, next_col, cur_dir)))

    row, col , cur_dist = next_row, next_col, new_dist

  return resp

def get_neighs(grid, row, col, cur_dir, cur_dist):
  possible_dirs  = get_next_possible_dirs(cur_dir)
  return list(itertools.chain.from_iterable([ get_dist_after_steps(grid, row, col, new_dir, cur_dist) for new_dir in possible_dirs ]))

def process(grid):
  distances = defaultdict(lambda: float('inf'))
  distances[(0, 0, 'left')] = 0
  distances[(0, 0, 'up')] = 0
  target = (len(grid) -1, len(grid[0])-1)
  seen = set()

  q = [(0, (0, 0, 'left')), (0, (0, 0, 'up'))]

  while q:
    [cur_dist, (row, col, cur_dir)] =  heapq.heappop(q)

    if (row, col) == target:
      return cur_dist

    if (row, col, cur_dir) in seen:
      continue
    seen.add((row, col, cur_dir))

    neighs_with_dist = get_neighs(grid, row, col, cur_dir, cur_dist)
    for (n_dist, n_pos) in neighs_with_dist:
      if n_dist < distances[n_pos]:
        distances[n_pos] = n_dist
        heapq.heappush(q, (n_dist, n_pos))
    
  raise("Not found")
    
def main():
  grid = [ [int(c) for c in line_str.strip()] for line_str in sys.stdin]
  resp = process(grid)
  print(resp)

if __name__ == '__main__':
    main()