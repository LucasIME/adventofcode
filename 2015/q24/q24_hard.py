from functools import reduce
from itertools import combinations

def get_qe(n_list):
    return reduce(lambda x,y: x*y, n_list, 1)

def process(entry_list):
    total_sum = sum(entry_list)
    target_sum = total_sum/4
    return has_sum(entry_list, 4, target_sum)

def has_sum(l, parts, target_sum):
    for n in range(1,len(l)):
        for comb in (z for z in combinations(l, n) if sum(z) == target_sum):
            if parts == 2:
                return True
            # 4 because problem want in 4 parts
            elif parts < 4:
                return has_sum(list(set(l) - set(comb)), parts - 1, target_sum)
            elif has_sum(list(set(l) - set(comb)), parts - 1, target_sum):
                return get_qe(comb)

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

