from collections import defaultdict

def correct_error(matrix):
    resp = []
    for col in range(len(matrix[0])):
        d = defaultdict(int)
        mincount = float('inf')
        least_frequent_letter = ''
        for row in range(len(matrix)):
            c = matrix[row][col]
            d[c] += 1
        for letter, count in d.items():
            if count <= mincount:
                least_frequent_letter = letter
                mincount = count
        resp.append(least_frequent_letter)
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
