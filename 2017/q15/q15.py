DIV = 2147483647

def get_next_a(a_cur):
    return (a_cur * 16807) % DIV

def get_next_b(b_cur):
    return (b_cur * 48271) % DIV

def is_match(a, b):
    return (a & 0xFFFF) == (b & 0xFFFF)

def get_count(a_start, b_start):
    a = a_start
    b = b_start
    resp = 0
    for i in range(40000000):
        a = get_next_a(a)
        b = get_next_b(b)
        if is_match(a, b):
            resp += 1
    return resp

def main():
    A_entry = int(input().split()[-1])
    B_entry = int(input().split()[-1])
    resp = get_count(A_entry, B_entry)
    print(resp)

if __name__ == '__main__':
    main()
