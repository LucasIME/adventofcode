from collections import defaultdict

def parse_right(item):
    return item.split(',')[0]

def build_graph(entry_list):
    graph = defaultdict(list)
    for entry in entry_list:
        left = entry[0]
        if len(entry) > 3:
            for i, item in enumerate(entry[3:]):
                graph[left].append(parse_right(item))
    return graph

def build_weight(entry_list):
    weight = {}
    for entry in entry_list:
        left = entry[0]
        w = int(entry[1][1:-1])
        weight[left] = w
    return weight

def get_tree_w(graph, root, weight):
    if root is None:
        return 0
    w = weight[root]
    for next in graph[root]:
        w += get_tree_w(graph, next, weight)
    return w

def build_rev(graph):
    rev = {}
    for key in graph:
        for val in graph[key]:
            rev[val] = key
    return rev

def get_leaves(graph):
    resp = []
    for key, neighs in graph.items():
        for neigh in neighs:
            if neigh not in graph:
                resp.append(neigh)
    return resp

def get_unbalanced_parent(graph, weight):
    rev_graph = build_rev(graph)
    leaves = get_leaves(graph)
    q = leaves
    visited = set()
    while q:
        cur = q.pop()
        visited.add(cur)
        parent = rev_graph[cur]
        child_vals = set()
        for child in graph[parent]:
            if not child_vals:
                child_vals.add(get_tree_w(graph, child, weight))
            else:
                child_w = get_tree_w(graph, child, weight)
                if child_w not in child_vals:
                    return parent

        if parent not in visited:
            q.insert(0, parent)

def get_unbalanced_child(graph, weight, parent):
    seen = defaultdict(list)
    for child in graph[parent]:
        seen[get_tree_w(graph, child, weight)].append(child)

    expected = 0
    cur = 0
    for value, l in seen.items():
        if len(l) == 1:
            cur = value
        else:
            expected = value

    diff = expected - cur

    return seen[cur], weight[seen[cur][0]] + diff

def get_new_weight(entry_list):
    root = get_root(entry_list)
    graph = build_graph(entry_list)
    weight = build_weight(entry_list)

    unbalanced_parent = get_unbalanced_parent(graph, weight)
    unbalanced_child, new_w = get_unbalanced_child(graph, weight, unbalanced_parent)
    return new_w

def get_root(entry_list):
    total = []
    is_right_rule = set()
    for entry in entry_list:
        total.append(entry[0])
        if len(entry) > 3:
            for i, item in enumerate(entry[3:]):
                is_right_rule.add(parse_right(item))
    for item in total:
        if item not in is_right_rule:
            return item
    return None

def main():
    entry = []
    while True:
        line = input()
        if line == '':
            break
        entry.append(line.split())
    resp = get_new_weight(entry)
    print(resp)

if __name__ == '__main__':
    main()
