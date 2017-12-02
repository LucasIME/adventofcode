def get_line_checksum(line):
    for i in range(len(line)):
        for j in range(i+1, len(line)):
            item1, item2 = line[i], line[j]
            if item1%item2 == 0:
                return int(item1/item2)
            if item2%item1 == 0:
                return int(item2/item1)

def get_checksum(matrix):
    resp = 0
    for line in matrix:
        line_checksum = get_line_checksum(line)
        resp += line_checksum
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

