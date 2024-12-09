from itertools import zip_longest

def parse_input():
    raw_input = [int(x) for x in input()]
    occupied = raw_input[::2]
    free = raw_input[1::2]
    return (occupied, free)

def combine(occupied, free):
    resp = []

    zipped = zip_longest(occupied, free, fillvalue=0)

    for i, (oc, space) in enumerate(zipped):
        for j in range(oc):
            resp.append(i)
        for j in range(space):
            resp.append("#")
    return resp

def count_last_n(entry, target, right):
    resp = 0
    while entry[right] == target:
        resp += 1
        right -= 1

    return resp


def count_start_n(entry, target, left):
    resp = 0
    while entry[left] == target:
        resp += 1
        left +=1 
        
    return resp


def compress(entry):
    right = len(entry) - 1
    while right >= 0:
        last = entry[right]
        if last == "#":
            right -= 1
            continue

        last_freq = count_last_n(entry, last, right)

        update_index = -1
        should_update = False
        for left in range(right+1):
            if entry[left] == "#":
                available_pos = count_start_n(entry, "#", left)
                if available_pos >= last_freq:
                    should_update = True
                    update_index = left
                    break
        
        if should_update:
            for i in range(last_freq):
                entry[update_index + i] = last
                entry[right - i] = "#"
        right -= last_freq

    return entry


def checksum(entry):
    resp = 0
    for i, x in enumerate(entry):
        val = 0 if x == "#" else x
        resp += i * val
    return resp

def process(input):
    (occupied, free) = input

    combined = combine(occupied, free)
    compressed = compress(combined)
    
    return checksum(compressed)

def main():
    entry = parse_input()
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()

