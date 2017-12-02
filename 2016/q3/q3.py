def count_possible(entry):
    total_possible = 0
    for triplet in entry:
        if triplet[1] + triplet[2] > triplet[0] and \
            triplet[0] + triplet[2] > triplet[1] and \
            triplet[0] + triplet[1] > triplet[2]:
            total_possible += 1
    return total_possible

def main():
    matrix = []
    while True:
        entry = input()
        if entry == '':
            break
        matrix.append([int(x) for x in entry.split()])
    resp = count_possible(matrix)
    print(resp)

if __name__ == '__main__':
    main()
