def get_checksum(matrix):
    resp = 0
    for line in matrix:
        mini, maxi = min(line), max(line)
        resp += maxi - mini
    return resp

def main():
    matrix = []
    while True:
        line = input()
        if line == '':
            break
        int_line = [int(x) for x in line.split()]
        matrix.append(int_line)
    resp = get_checksum(matrix)
    print(resp)

if __name__ == '__main__':
    main()

