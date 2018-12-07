from collections import defaultdict

def parse(entry_list):
    all = set()
    d = defaultdict(set)
    for entry in entry_list:
        d[entry[-3]].add(entry[1])
        all.add(entry[1])
        all.add(entry[-3])
    return d, all

def add_req(requirements, nodes):
    for node in nodes:
        if node not in requirements:
            requirements[node] = set()
    return requirements

def remove_deps(requirements, node):
    for key in requirements:
        requirements[key].discard(node)
    del requirements[node]
    return requirements

def get_next_steps(requirements, time):
    chosen = []
    for key, v in requirements.items():
        if not v:
            chosen.append(key)
    chosen.sort()
    return chosen


def get_time_to_complete(requirements, time, workers):
    total = 0
    while requirements:
        steps = get_next_steps(requirements, time)
        total += 1
        for node in steps[:workers]:
            time[node] -= 1

        del_list = []
        for node, v in time.items():
            if v == 0:
                del_list.append(node)

        for node in del_list:
            del time[node]
            requirements = remove_deps(requirements, node)

    return total

def get_times(nodes):
    time = {}
    for letter in nodes:
        time[letter] = 60 + ord(letter) - ord('A') + 1
    return time

def process(entry_list):
    WORKERS = 5
    requirements, nodes = parse(entry_list)
    requirements = add_req(requirements, nodes)
    time = get_times(nodes)
    return get_time_to_complete(requirements, time, WORKERS)

def main():
    entry_list = []
    while True:
       line = input()
       if line == '':
           break
       entry_list.append(line.split(' '))
    resp = process(entry_list)
    print(resp)

if __name__ == '__main__':
    main()

