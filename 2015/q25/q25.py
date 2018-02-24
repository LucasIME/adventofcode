BASE = 20151125
FACTOR = 252533
MOD = 33554393

def get_nth_item(n):
    resp = BASE
    for i in range(1, n):
        resp = (resp * FACTOR) % MOD
    return resp

def pa_sum_til(n):
    return ((1+n)*n)//2

def get_pos(row, col):
    base = pa_sum_til(col)
    resp = base

    #same as: resp += (row-1)*(col-1) + ((row-1)*row)//2
    for i in range(1, row):
        resp += col + i - 1
    return resp

def get_num(row, col):
    pos = get_pos(row, col)
    return get_nth_item(pos)

def process(entry):
    row, col = parse(entry)
    return get_num(row, col)

def parse(entry):
    row = int(entry.split()[-3][:-1])
    col = int(entry.split()[-1][:-1])
    return row, col

def main():
    entry = input()
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()
