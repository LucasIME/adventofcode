from collections import defaultdict
from itertools import permutations

def parse(entry_list):
    d = defaultdict(dict)
    for entry in entry_list:
        s = entry.split()
        p1 = s[0]
        p2 = s[-1][:-1]
        action = s[2]
        happiness = int(s[3])
        if action == 'gain':
            d[p1][p2] = happiness
        elif action == 'lose':
            d[p1][p2] = -1 * happiness
    return d

def add_myself(graph):
    keys = list(graph.keys())
    for key in keys:
        graph[key]['Myself'] = 0
        graph['Myself'][key] = 0
    return graph

def get_arrangement_happiness(arrangement, graph):
    hap = 0
    for i, p1 in enumerate(arrangement):
        hap += graph[p1][arrangement[(i-1)%len(arrangement)]]
        hap += graph[p1][arrangement[(i+1)%len(arrangement)]]
    return hap

def process_entry(entry_list):
    graph = parse(entry_list)
    graph = add_myself(graph)
    max_hap = 0
    for sitting_arrangement in permutations(graph.keys()):
        arrangement = list(sitting_arrangement)
        max_hap = max(max_hap, get_arrangement_happiness(arrangement, graph))
    return max_hap

def main():
    entry_list = []
    while True:
        entry = input()
        if entry == '':
            break
        entry_list.append(entry)
    resp = process_entry(entry_list)
    print(resp)

if __name__ == '__main__':
    main()
