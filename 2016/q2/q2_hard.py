PAD = [
    [None, None, '1', None, None],
    [None, '2', '3', '4', None],
    ['5', '6', '7', '8', '9'],
    [None, "A", "B", "C", None],
    [None, None, "D", None, None]
]

def is_valid(row, col):
    return (abs(row-2) + abs(col-2)) <= 2

def try_move(row, col, move):
    if move == 'U':
        return (row-1, col) if is_valid(row-1, col) else (row, col)
    if move == 'R':
        return (row, col+1) if is_valid(row, col+1) else (row, col)
    if move == 'L':
        return (row, col-1) if is_valid(row, col-1) else (row, col)
    if move == 'D':
        return (row+1, col) if is_valid(row+1, col) else (row, col)

def get_code(line_array):
    row = 2
    col = 0
    code = []
    for line in line_array:
        for move in line:
            row, col = try_move(row, col, move)
        code.append(PAD[row][col])
    return ''.join(code)

def main():
    matrix = []
    while True:
        entry = input()
        if entry == "":
            break
        matrix.append([letter for letter in entry])
    resp = get_code(matrix)
    print(resp)

if __name__ == '__main__':
    main()
