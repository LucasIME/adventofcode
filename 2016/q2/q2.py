PAD = [['1', '2', '3'], ['4', '5', '6'], ['7', '8', '9']]

def try_move(row, col, move):
    if move == 'U':
        return (max(row-1, 0), col)
    if move == 'R':
        return (row, min(col+1, 2))
    if move == 'L':
        return (row, max(col-1, 0))
    if move == 'D':
        return (min(row+1, 2), col)

def get_code(line_array):
    row = 1
    col = 1
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
