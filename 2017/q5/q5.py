def count_steps_out(entry):
    total = 0
    i = 0
    while i < len(entry) and i >= 0:
        next = entry[i]
        entry[i] += 1
        i += next
        total += 1
    return total

def main():
    entry = []
    while True:
        line = input()
        if line == '':
            break
        entry.append(int(line))
    resp = count_steps_out(entry)
    print(resp)

if __name__ == '__main__':
    main()
