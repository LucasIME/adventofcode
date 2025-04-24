from math import sqrt

def is_prime(x):
    if x == 2 or  x == 3:
        return True

    for i in range(2, int(sqrt(x)+1)):
        if x % i == 0:
            return False

    return True

def main():
    start = 84 * 100 + 100_000
    end = start + 17_000 + 17

    valid = []
    for x in range(start, end, 17):
        if not is_prime(x):
            valid.append(x)

    print(len(valid))


if __name__ == '__main__':
    main()
