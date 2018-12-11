WIDTH = 300
HEIGHT = 300

def get_power_level(x, y, my_input):
    rack_id = x + 10
    power = rack_id * y
    power += my_input
    power *= rack_id
    power = (power // 100) % 10
    power -= 5
    return power

def get_score(matrix, pos_pair):
    if pos_pair[0] < 0 or pos_pair[1] < 0:
        return 0
    return matrix[pos_pair[1]][pos_pair[0]]

def get_square_sum(matrix, col, row, size):
    if col < 0 or row < 0:
        return 0

    top_left = (col - 1, row - 1)
    bot_right = (col + size -1, row + size - 1)
    top_right = (col + size -1, row - 1)
    bot_left = (col - 1, row + size - 1)
    score = get_score(matrix, bot_right) - get_score(matrix, top_right) - get_score(matrix, bot_left) + get_score(matrix, top_left)
    return score

def get_best_square(matrix):
    best = float('-inf')
    chosen = (None, None, None)
    for row in range(HEIGHT):
        for col in range(WIDTH):
            for size in range(1, min(HEIGHT - row, WIDTH - col)):
                score = get_square_sum(matrix, col, row, size)
                if score > best:
                    best = score
                    chosen = (col+1, row+1, size)
    return chosen

def normalize(matrix):
    for row in range(HEIGHT):
        for col in range(WIDTH):
            if row == 0 and col == 0:
                continue
            elif row == 0:
                matrix[row][col] += matrix[row][col-1]
            elif col == 0:
                matrix[row][col] += matrix[row-1][col]
            else:
                matrix[row][col] += matrix[row][col-1] + matrix[row-1][col] - matrix[row-1][col-1]
    return matrix

def process(entry):
    matrix = [[0 for i in range(WIDTH)] for j in range(HEIGHT)]
    for row in range(HEIGHT):
        for col in range(WIDTH):
            matrix[row][col] = get_power_level(col+1, row+1, entry)
    matrix = normalize(matrix)
    return get_best_square(matrix)

def main():
    entry = int(input())
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()

