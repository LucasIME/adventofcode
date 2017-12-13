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

def expand(key, d, key_to_group, group_name):
    q = [key]
    visited = set()
    while q:
        cur = q.pop()
        visited.add(cur)
        key_to_group[cur] = group_name
        for neigh in d[cur]:
            if neigh not in visited:
                q.append(neigh)

def get_group(key, d, key_to_group, group_name):
    if key in key_to_group:
        return key_to_group[key]

    expand(key, d, key_to_group, group_name)
    return key_to_group[key]

def get_groups(entry):
    d = build_graph(entry)
    key_to_group = {}
    resp = 0
    for key in d:
        if key in key_to_group:
            continue
        resp += 1
        group = get_group(key, d, key_to_group, resp)
    return resp

def main():
    entry = []
    while True:
        line = input()
        if line == '':
            break
        entry.append(line)
    resp = get_groups(entry)
    print(resp)

if __name__ == '__main__':
    main()
