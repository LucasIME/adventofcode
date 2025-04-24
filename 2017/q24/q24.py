import sys

def flip(piece):
    (a, b) = piece
    return (b, a)

def is_compatible(piece1, piece2):
    (a, b) = piece1
    (c, d) = piece2

    return c == b

def is_startable(piece):
    (a, b) = piece

    return a == 0

def parse(file_name: str):
    result = []

    with open(file_name, 'r') as file:
        for line in file.readlines():
            stripped_line = line.strip()
            if stripped_line == '':
                continue
            [a, b] = line.split('/')
            result.append((int(a), int(b)))
    return result

def score(pieces):
    scores = [a + b for (a, b) in pieces]
    return sum(scores)

def find_max(cur_piece, remaining_set, acc=[]):
    if len(remaining_set) == 0:
        return score(acc)

    resp = 0
    is_last = True
    for piece in remaining_set:
        if is_compatible(cur_piece, piece):
            is_last = False
            new_set = remaining_set.difference([piece])
            acc.append(piece)
            resp = max(resp, find_max(piece, new_set, acc))
            acc.pop()
        if is_compatible(cur_piece, flip(piece)):
            is_last = False
            new_set = remaining_set.difference([piece])
            acc.append(flip(piece))
            resp = max(resp, find_max(flip(piece), new_set, acc))
            acc.pop()

    if is_last:
        resp = max(resp, score(acc))

    return resp
    

def part_1(file_name: str):
    pieces = parse(file_name)

    startables = [piece for piece in pieces if is_startable(piece)]

    resp = 0
    for start in startables:
        start_set = set(pieces)
        start_set.remove(start)
        resp = max(resp, find_max(start, start_set, [start]))
    
    return resp

if __name__ == '__main__':
    file_name = sys.argv[1]
    resp = part_1(file_name)
    print(resp)
