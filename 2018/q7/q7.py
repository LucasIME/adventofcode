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
    return requirements

def get_next_step(requirements):
    chosen = 'Z9'
    for key, v in requirements.items():
        if not v:
            if key < chosen:
                chosen = key
    del requirements[chosen]
    return chosen, remove_deps(requirements, chosen)


def get_path(requirements):
    path = []
    while requirements:
        step, new_req = get_next_step(requirements)
        path.append(step)
        requirements = new_req
    return path

def process(entry_list):
    requirements, nodes = parse(entry_list)
    requirements = add_req(requirements, nodes)
    return ''.join(get_path(requirements))

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

