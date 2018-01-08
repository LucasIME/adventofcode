from collections import defaultdict

def parse(entry):
    entry = entry.split('=')
    distance = int(entry[-1].split()[0])
    locations = entry[0].split()
    source = locations[0]
    dest = locations[-1]

    return {'source':source, 'dest':dest, 'distance':distance}

def build_graph(entry_list):
    d = defaultdict(dict)
    for entry in entry_list:
        d[entry['source']][entry['dest']] = entry['distance']
        d[entry['dest']][entry['source']] = entry['distance']
    return d

def get_smallest_path(g, node_set):
    maxi = 0
    for node in node_set:
        maxi = max(maxi, helper(g, node, set(), set(node_set)))
    return maxi

def helper(g, cur, already_visited, to_visit):
    already_visited.add(cur)
    to_visit.remove(cur)

    if to_visit == set():
        return 0

    maxi = 0
    for next_node in set(to_visit):
        maxi = max(maxi, g[cur][next_node] + helper(g, next_node, already_visited, to_visit))
        already_visited.remove(next_node)
        to_visit.add(next_node)

    return maxi

def process(entry_list):
    g = build_graph(entry_list)
    node_set = set(key for key in g)
    return get_smallest_path(g, node_set)

def main():
    entry_list = []
    while True:
        entry = input()
        if entry == '':
            break
        entry_list.append(parse(entry))
    resp = process(entry_list)
    print(resp)

if __name__ == '__main__':
    main()
