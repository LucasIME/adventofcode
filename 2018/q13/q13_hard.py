dirs = ['<', '^', '>', 'v']
CRASHED = ("CRASHED", "CRASHED", "CRASHED")

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

def is_crashed(car, positions_to_delete):
    if (car[0], car[1]) in positions_to_delete:
        return True
    return False

def collide(cars):
    seen_positions = set()
    positions_to_delete = set()
    for car in cars:
        row, col, dir = car
        if (row, col) in seen_positions:
            positions_to_delete.add((row, col))
        seen_positions.add((row, col))

    if positions_to_delete:
        cars = list(map(lambda x: CRASHED if is_crashed(x, positions_to_delete) else x, cars))

    return cars

def delete_collided(cars, cars_next):
    all_index = { i:True for i in range(len(cars)) }
    indexis_of_colided = {}
    for i, car in enumerate(cars):
        if car == CRASHED:
            indexis_of_colided[i] = True
            del all_index[i]

    cars = [cars[i] for i in all_index]
    cars_next = [cars_next[i] for i in all_index]

    return cars, cars_next

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
    if car == CRASHED:
        return

    row, col, dir = car
    next_row, next_col = get_next_cell_in_dir(row, col, dirs[dir])
    new_dir = update_dir(matrix, next_row, next_col, car, cars_next, index)
    cars[index] = (next_row, next_col, new_dir)

import time

def process(entry_list):
    matrix = entry_list
    cars = find_cars(matrix)
    cars_next = [-1 for x in cars]
    while len(cars) > 1:
        for i in range(len(cars)):
            move_car(i, matrix, cars, cars_next)
            cars = collide(cars)
        cars, cars_next = delete_collided(cars, cars_next)
        cars, cars_next = zip(*sorted(zip(cars, cars_next)))
        cars, cars_next = list(cars), list(cars_next)

    return (cars[0][1], cars[0][0])

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

