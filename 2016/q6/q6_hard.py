from collections import defaultdict
import heapq

def correct_error(matrix):
    resp = []
    for col in range(len(matrix[0])):
        d = defaultdict(int)
        h = []
        for row in range(len(matrix)):
            c = matrix[row][col]
            d[c] += 1
        for letter, count in d.items():
            heapq.heappush(h, (count, letter))
        least_frequent_letter = heapq.heappop(h)[1]
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
