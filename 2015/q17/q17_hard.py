def get_comb(entry_list):
    maxi = 2 ** len(entry_list)
    target_sum = 150
    resp = 0
    mini = float('inf')
    for base_i in range(maxi):
        cur = base_i
        cur_i = 0
        cur_sum = 0
        bucket_n = 0
        while cur > 0:
            if cur & 1:
                cur_sum += entry_list[cur_i]
                bucket_n += 1
            cur >>= 1
            cur_i += 1
        if cur_sum == target_sum and bucket_n < mini:
            mini = bucket_n
            resp = 0

        if cur_sum == target_sum and bucket_n == mini:
            resp += 1
    return resp

def main():
    entry_list = []
    while True:
        entry = input()
        if entry == '':
            break
        entry_list.append(int(entry))
    resp = get_comb(entry_list)
    print(resp)

if __name__ == '__main__':
    main()
