def is_valid(board, row, col):
    return row >= 0 and row < len(board) and col >= 0 and col < len(board[row])

def find_start(board):
    for i, row in enumerate(board):
        for j, c in enumerate(row):
            if c != ' ':
                return i, j

def try_move_dir(row, col, dir):
    if dir == 'down':
        return row+1, col
    elif dir == 'up':
        return row-1, col
    elif dir == 'right':
        return row, col+1
    elif dir == 'left':
        return row, col-1

def get_perpendicular(dir):
    hori = ['right', 'left']
    vert = ['up', 'down']
    if dir in hori:
        return vert
    return hori

def get_next(board, row, col, dir):
    try_row, try_col = try_move_dir(row, col, dir)
    if is_valid(board, try_row, try_col) and board[try_row][try_col] != ' ':
        return try_row, try_col, dir

    directions = get_perpendicular(dir)
    for new_dir in directions:
        try_row, try_col = try_move_dir(row, col, new_dir)
        if is_valid(board, try_row, try_col) and board[try_row][try_col] != ' ':
            return try_row, try_col, new_dir

    return None, None, None

def get_path(board):
    row, col = find_start(board)
    dir = 'down'
    resp = 0
    while True:
        row, col, dir = get_next(board, row, col, dir)
        resp += 1
        if row is None or col is None or dir is None:
            break
        current = board[row][col]
        if current == ' ':
            break
    return resp

def main():
    entry = []
    while True:
        line = input()
        if line == '':
            break
        entry.append(line)
    resp = get_path(entry)
    print(resp)

if __name__ == '__main__':
    main()
