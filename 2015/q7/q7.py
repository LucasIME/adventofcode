from collections import defaultdict

def build_dependency_graph(entry_list):
    dependency = defaultdict(list)
    for entry in entry_list:
        left, right = entry
        right = right[0]
        for entry in get_names_left(left):
            dependency[right].append(entry)
    return dependency

def build_rules(entry_list):
    rules = {}
    for entry in entry_list:
        left, right = entry
        rules[right[0]] = left
    return rules

def get_names_left(left):
    if len(left) == 1:
        op = try_get_name(left[0])
        return [] if op is None else [op]
    elif len(left) == 2:
        op = try_get_name(left[1])
        return [] if op is None else [op]
    elif len(left) == 3:
        resp = []
        op1 = try_get_name(left[0])
        op2 = try_get_name(left[2])
        if op1 is not None:
            resp.append(op1)
        if op2 is not None:
            resp.append(op2)
        return resp

def try_get_val(name, d):
    try:
        return int(name)
    except:
        return d[name]

def try_get_name(name):
    try:
        x = int(name)
        return None
    except:
        return name

def not16(val):
    return ~val & 0xffff

def process(key, dependency, rules, cache):
    if key in cache:
        return cache[key]

    if key not in dependency or len(dependency[key]) == 0:
        cache[key] = process_left(rules[key], cache)
        return cache[key]

    while dependency[key]:
        cur = dependency[key].pop()
        process(cur, dependency, rules, cache)
    cache[key] = process_left(rules[key], cache)
    return cache[key]

def process_left(l_value, cache):
    if len(l_value) == 1:
        return try_get_val(l_value[0], cache)
    elif len(l_value) == 2:
        if l_value[0] == 'NOT':
            return not16(try_get_val(l_value[1], cache))
    elif len(l_value) == 3:
        op1 = try_get_val(l_value[0], cache)
        op2 = try_get_val(l_value[2], cache)
        if l_value[1] == 'AND':
            return op1 & op2
        elif l_value[1] == 'OR':
            return op1 | op2
        elif l_value[1] == 'LSHIFT':
            return op1 << op2
        elif l_value[1] == 'RSHIFT':
            return op1 >> op2

def process_entry(entry_list):
    dependency = build_dependency_graph(entry_list)
    rules = build_rules(entry_list)
    cache = {}
    for entry in entry_list:
        left, right = entry
        right = right[0]
        process(right, dependency, rules, cache)
    return cache['a']

def main():
    entry_list = []
    while True:
        entry = input()
        if entry == '':
            break
        entry_list.append([x.split() for x in entry.split('->')])
    resp = process_entry(entry_list)
    print(resp)

if __name__ == '__main__':
    main()
