ON = '#'
OFF = '.'
height = 6
width = 50
screen = [[OFF for i in range(width)] for j in range(height)]

def count_pixels(screen):
    total = 0
    for row in screen:
        for item in row:
            if item == ON:
                total += 1
    return total

def get_col(matrix, index):
    return [row[index] for row in matrix]

def rect(w, h):
    for row in range(h):
        for col in range(w):
            screen[row][col] = ON

def rotate_column(col, n):
    anticol = reverse(get_col(screen, col))
    finalcol = reverse(anticol[:n]) + reverse(anticol[n:])
    for i in range(len(screen)):
        screen[i][col] = finalcol[i]

def reverse(l):
    return l[::-1]

def rotate_row(row, n):
    antirow = reverse(screen[row])
    screen[row] = reverse(antirow[:n]) + reverse(antirow[n:])

def process_entries(entry_list):
    for command in entry_list:
        if command[0] == 'rect':
            w, h = [int(x) for x in command[1].split('x')]
            rect(w, h)
        elif command[1] == 'row':
            row = int(command[2].split('=')[1])
            n = int(command[-1])
            rotate_row(row, n)
        else:
            col = int(command[2].split('=')[1])
            n = int(command[-1])
            rotate_column(col, n)

def main():
    entry_list = []
    while True:
        entry = input()
        if entry == '':
            break
        entry_list.append(entry.split())
    process_entries(entry_list)
    resp = count_pixels(screen)
    print(resp)

if __name__ == '__main__':
    main()
