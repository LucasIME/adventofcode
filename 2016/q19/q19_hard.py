from collections import deque

def process(n):
    left = deque() # stack
    for i in range(1, n//2 + 1):
        left.append(i)
    right = deque() # queue
    for i in range(n//2 + 1, n + 1):
        right.appendleft(i)

    while left and right:
        if len(left) > len(right):
            left.pop()
        else:
            right.pop()

        right.appendleft(left.popleft())
        left.append(right.pop())

    return left[0] if left else right[0]

def main():
    entry = int(input())
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()
