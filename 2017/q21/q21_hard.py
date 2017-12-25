import re
from math import sqrt

pattern = re.compile('p=<(.*)>, v=<(.*)>, a=<(.*)>')

def hash(world):
    resp = []
    for line in world:
        resp.append(''.join(line))
    return '/'.join(resp)

def world_from_hash(m_hash):
    lines = m_hash.split('/')
    m = []
    for line in lines:
        m.append(list(line))
    return m

def build_rules(entry):
    d = {}
    for line in entry:
        key, v = [x.strip() for x in line.split('=>')]
        d[key] = v
    return d

def split_size(world, size):
    splits = []
    for startrow in range(0, len(world), size):
        for startcol in range(0, len(world[startrow]), size):
            cur_split = []
            for row in range(startrow, startrow+size):
                cur_split.append(world[row][startcol:startcol+size])
            splits.append(cur_split)
    return splits

def get_splits(world):
    if len(world) % 2 == 0:
        return split_size(world, 2)
    elif len(world) % 3 == 0:
        return split_size(world, 3)

def rotate(pattern):
    out = world_from_hash(pattern)
    out2 = world_from_hash(pattern)
    for i in range(len(out)):
        for j in range(len(out[i])):
            out[i][j] = out2[len(out2) - j - 1][i]
    return hash(out)

def flip(pattern):
    parts = pattern.split('/')
    return '/'.join([x[::-1] for x in parts])

def rotates_and_flips(pattern):
    out = set()
    out.add(pattern)
    flipped = flip(pattern)
    out.add(flipped)
    cur = pattern
    for i in range(3):
        cur = rotate(cur)
        out.add(cur)
    cur = flipped
    for i in range(3):
        cur = rotate(cur)
        out.add(cur)
    return out

def transform(splits, rules):
    resp = []
    for split in splits:
        h = hash(split)
        for isomer in rotates_and_flips(h):
            if isomer in rules:
                resp.append(rules[isomer])
                break
    return [world_from_hash(h) for h in resp]

def join(splits):
    if len(splits) == 1:
        return splits[0]
    line_size = int(sqrt(len(splits)))
    out = [['2' for i in range(line_size*len(splits[0]))] for j in range(line_size*len(splits[0]))]
    for i in range(len(splits)):
        cur = splits[i]
        for row in range(len(cur)):
            for col in range(len(cur[row])):
                out_row = (i//line_size)*len(cur) + row
                out_col = (i % line_size)*len(cur) + col
                out[out_row][out_col] = cur[row][col]
    return out

def get_pixels_after(entry, count):
    rules = build_rules(entry)
    m = [
        ['.', '#', '.'],
        ['.', '.', '#'],
        ['#', '#', '#']
    ]
    for i in range(count):
        splits = get_splits(m)
        splits = transform(splits, rules)
        m = join(splits)
    return count_pixels(m)

def count_pixels(m):
    resp = 0
    for line in m:
        for item in line:
            if item == '#':
                resp += 1
    return resp

def main():
    entry = []
    while True:
        line = input()
        if line == '':
            break
        entry.append(line)
    resp = get_pixels_after(entry, 18)
    print(resp)

if __name__ == '__main__':
    main()
