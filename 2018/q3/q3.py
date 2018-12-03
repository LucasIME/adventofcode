def parse_line(line):
    right = line.split('@ ')
    raw = right[1].split(': ')
    coords = raw[0].split(',')
    size = raw[1].split('x')
    x, y = [int(x) for x in coords]
    width, height = [int(x) for x in size]
    return x, y, width, height

def parse(entry_list):
    return [parse_line(line) for line in entry_list]

def process(entry_list):
    matrix = [[0 for col in range(1000)] for row in range(1000)]
    entry_list = parse(entry_list)
    for line in entry_list:
        x, y, width, height = line
        for w in range(width):
            for h in range(height):
                matrix[y+h][x+w] += 1

    resp = 0
    for row in matrix:
        for val in row:
            if val >= 2:
                resp += 1

    return resp

def main():
    entry_list = []
    while True:
       line = input()
       if line == '':
           break
       entry_list.append(line)
    resp = process(entry_list)
    print(resp)

if __name__ == '__main__':
    main()

