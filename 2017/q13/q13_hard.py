def get_wait(entry):
    scan_max = {}
    width = 0
    for line in entry:
        col, size = [int(x) for x in line.split(': ')]
        scan_max[col] = size
        width = col
    wait = 0
    while True:
        caught = False
        for i in range(width+1):
            if i in scan_max:
                if (wait + i) % (2*scan_max[i]-2) == 0:
                    caught = True
                    break
        if caught is False:
            return wait
        wait += 1

def main():
    entry = []
    while True:
        line = input()
        if line == '':
            break
        entry.append(line)
    resp = get_wait(entry)
    print(resp)

if __name__ == '__main__':
    main()
