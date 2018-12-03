def parse_line(line):
    right = line.split(' @ ')
    id = int(right[0][1:])
    raw = right[1].split(': ')
    coords = raw[0].split(',')
    size = raw[1].split('x')
    x, y = [int(x) for x in coords]
    width, height = [int(x) for x in size]
    return id, x, y, width, height

def parse(entry_list):
    return [parse_line(line) for line in entry_list]

def do_intersect(x, y, width, height, x2, y2, width2, height2):
    return not (x+width < x2 or y+height < y2 or x2+width2 < x or y2+height2 < y)

def process(entry_list):
    entry_list = parse(entry_list)
    for line in entry_list:
        is_resp = True
        id, x, y, width, height = line
        for line2 in entry_list:
            id2, x2, y2, width2, height2 = line2

            if id2 == id:
                continue

            if do_intersect(x, y, width, height, x2, y2, width2, height2):
                is_resp = False
                break

        if is_resp:
            return id

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

