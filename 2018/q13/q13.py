dirs = ['<', '^', '>', 'v']

def find_cars(matrix):
    cars = []
    for row, line in enumerate(matrix):
        for col, direction in enumerate(line):
            if direction in dirs:
                cars.append((row, col, dirs.index(direction)))
    return cars

def collide(cars):
    seen = set()
    for car in cars:
        row, col, dir = car
        if (row, col) in seen:
            return True, row, col
        seen.add((row, col))
    return False, -1, -1

def get_next_cell_in_dir(row, col, dir):
    if dir == '<':
        return row, col-1
    if dir == '>':
        return row, col+1
    if dir == '^':
        return row-1, col
    if dir == 'v':
        return row+1, col

def update_dir(matrix, next_row, next_col, car, cars_next, car_index):
    cell = matrix[next_row][next_col]
    cur_dir = car[2]
    if cell == '/':
        if dirs[cur_dir] == '<':
            return 3
        if dirs[cur_dir] == '^':
            return 2
        if dirs[cur_dir] == '>':
            return 1
        if dirs[cur_dir] == 'v':
            return 0
    elif cell == '\\':
        if dirs[cur_dir] == '<':
            return 1
        if dirs[cur_dir] == '^':
            return 0
        if dirs[cur_dir] == '>':
            return 3
        if dirs[cur_dir] == 'v':
            return 2
    elif cell == '+':
        resp = (cur_dir + cars_next[car_index]) % 4
        cars_next[car_index] += 1
        if cars_next[car_index] == 2:
            cars_next[car_index] = -1
        return resp
    return cur_dir

def move_car(index, matrix, cars, cars_next):
    car = cars[index]
    row, col, dir = car
    next_row, next_col = get_next_cell_in_dir(row, col, dirs[dir])
    new_dir = update_dir(matrix, next_row, next_col, car, cars_next, index)
    cars[index] = (next_row, next_col, new_dir)

import time

def process(entry_list):
    matrix = entry_list
    cars = find_cars(matrix)
    cars_next = [-1 for x in cars]
    while True:
        for i in range(len(cars)):
            move_car(i, matrix, cars, cars_next)
            has_collided, row, col = collide(cars)
            if has_collided:
                return (col, row)

def main():
    entry_list = []
    while True:
       line = input()
       if line == '' :
               break
       entry_list.append(line)
    resp = process(entry_list)
    print(resp)

if __name__ == '__main__':
    main()

