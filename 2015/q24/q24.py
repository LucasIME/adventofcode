from functools import reduce
from itertools import combinations

def get_qe(n_list):
    return reduce(lambda x,y: x*y, n_list, 1)

def process(entry_list):
    total_sum = sum(entry_list)
    target_sum = total_sum/3
    min_len = float('inf')
    min_qe = float('inf')
    for group1, group2, group3 in gen_groups(entry_list, target_sum):
        if len(group1) > min_len:
            break
        min_len = len(group1)
        qe = get_qe(group1)
        min_qe = min(qe, min_qe)
        print(group1, group2, group3, qe, min_qe)

    return min_qe

def gen_groups(l, target_sum):
    for n in range(1, len(l)):
        for combination in combinations(l, n):
            g1 = set(combination)
            if sum(g1) != target_sum:
                continue
            remaining = set(l).difference(g1)
            for possible, g2, g3 in split_half(remaining, target_sum):
                if possible:
                    yield g1, g2, g3
    yield [], [], []

def split_half(l, target_sum):
    l_set = set(l)
    for n in range(1, len(l)):
        for combination in combinations(l, n):
            s1 = set(combination)
            if sum(s1) != target_sum:
                continue
            yield (True, list(s1), list(l_set.difference(s1)))
    yield (False, [], [])

def main():
    entry_list = []
    while True:
        entry = input()
        if entry == '':
            break
        entry_list.append(int(entry))
    resp = process(entry_list)
    print(resp)

if __name__ == '__main__':
    main()
