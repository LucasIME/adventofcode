def get_next_state(state, pos1, pos2, size):
    next_sum = state[pos1] + state[pos2]
    to_add = []
    if next_sum < 10:
        to_add.append(next_sum)
        state[size] = next_sum
    else:
        to_add.append(next_sum//10)
        to_add.append(next_sum%10)
        state[size] = next_sum//10
        state[size+1] = next_sum%10

    size = size + len(to_add)
    new_pos1 = (pos1 + state[pos1] + 1) % size
    new_pos2 = (pos2 + state[pos2] + 1) % size
    return state, new_pos1, new_pos2, size

def get_last(state, size, n):
    if n > size:
        return ''
    resp = ''.join([str(x) for x in state[size-n:size]])
    return resp

def process(entry):
    state = [3, 7] + ['x' for x in range(25000000)]
    pos1 = 0
    pos2 = 1
    size = 2
    while True:
        state, pos1, pos2, size = get_next_state(state, pos1, pos2, size)
        if get_last(state, size, len(entry)) == entry:
            return size - len(entry)
        if get_last(state, size-1, len(entry)) == entry:
            return size - len(entry) - 1

def main():
    entry = input()
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()

