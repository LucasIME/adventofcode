from collections import defaultdict

def correct_error(matrix):
    resp = []
    for col in range(len(matrix[0])):
        d = defaultdict(int)
        maxcount = 0
        most_frequent_letter = ''
        for row in range(len(matrix)):
            c = matrix[row][col]
            d[c] += 1
        for letter, count in d.items():
            if count >= maxcount:
                most_frequent_letter = letter
                maxcount = count
        resp.append(most_frequent_letter)
    return ''.join(resp)

def main():
    matrix = []
    while True:
        entry = input()
        if entry == "":
            break
        matrix.append([letter for letter in entry])
    resp = correct_error(matrix)
    print(resp)

if __name__ == '__main__':
    main()
