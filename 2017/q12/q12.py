from collections import defaultdict

def parse_dest(dest):
    d_split = dest.split(',')
    resp = [x.split()[0] for x in d_split]
    return resp

def parse_line(line):
    origin, dest = line.split('<->')
    origin = origin.split()[0]
    dest = parse_dest(dest)
    return origin, dest

def build_graph(entry):
    d = defaultdict(list)
    for line in entry:
        origin, dest = parse_line(line)
        d[origin] += dest
    return d

def count_expand(d, id):
    q = [id]
    visited = set()
    while q:
        cur = q.pop()
        visited.add(cur)
        for neigh in d[cur]:
            if neigh not in visited:
                q.append(neigh)
    return len(visited)

def get_group_size(entry, id):
    d = build_graph(entry)
    count = count_expand(d, id)
    return count

def main():
    entry = []
    while True:
        line = input()
        if line == '':
            break
        entry.append(line)
    resp = get_group_size(entry, '0')
    print(resp)

if __name__ == '__main__':
    main()
