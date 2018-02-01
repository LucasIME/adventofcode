from copy import deepcopy

ON = '#'
OFF = '.'

def is_valid(matrix, row, col):
    return row >= 0 and row < len(matrix) and col >= 0 and col < len(matrix[row])

def count_neigh(matrix, row, col):
    total = 0
    for c_row in range(row-1, row+2):
        for c_col in range(col-1, col+2):
            if c_row == row and c_col == col:
                continue
            if is_valid(matrix, c_row, c_col):
                if matrix[c_row][c_col] == ON:
                    total += 1
    return total

def update_item(matrix, row, col):
    neighs = count_neigh(matrix, row, col)
    cur_state = matrix[row][col]
    if cur_state == ON:
        if neighs == 2 or neighs == 3:
            return ON
        return OFF
    elif cur_state == OFF:
        if neighs == 3:
            return ON
        return OFF

def update(matrix):
    new_matrix = deepcopy(matrix)
    for row, line in enumerate(matrix):
        for col, item in enumerate(line):
            new_matrix[row][col] = update_item(matrix, row, col)
    return new_matrix

def count_on(matrix):
    total = 0
    for row in matrix:
        for item in row:
            if item == ON:
                total += 1
    return total

def get_comb(entry_list):
    matrix = [list(entry) for entry in entry_list]
    for time in range(100):
        matrix = update(matrix)
    return count_on(matrix)

def main():
    entry_list = []
    while True:
        entry = input()
        if entry == '':
            break
        entry_list.append(entry)
    resp = get_comb(entry_list)
    print(resp)

if __name__ == '__main__':
    main()
