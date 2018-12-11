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

def get_square_sum(matrix, col, row):
    score = 0
    for r in range(row, row+3):
        for c in range(col, col+3):
            score += matrix[r][c]
    return score

def get_best_square(matrix):
    best = float('-inf')
    chosen = (None, None)
    for row in range(HEIGHT - 3):
        for col in range(WIDTH - 3):
            score = get_square_sum(matrix, col, row)
            if score > best:
                best = score
                chosen = (col+1, row+1)
    return chosen

def process(entry):
    matrix = [[0 for i in range(WIDTH)] for j in range(HEIGHT)]
    for row in range(HEIGHT):
        for col in range(WIDTH):
            matrix[row][col] = get_power_level(col+1, row+1, entry)
    return get_best_square(matrix)

def main():
    entry = int(input())
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()

