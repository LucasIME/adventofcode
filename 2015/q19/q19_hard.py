from collections import defaultdict

def count_str(base, substring):
    count = 0
    index = base.find(substring)
    while index != -1:
        count += 1
        index = base.find(substring, index + 1)
    return count

def solve(mappings, target):
    total = sum(1 for c in target if c.isupper())
    rnCount = count_str(target, "Rn")
    arCount = count_str(target, "Ar")
    yCount = count_str(target, "Y")

    return total - rnCount - arCount - 2 * yCount - 1

def parse(file_name):
    with open(file_name, 'r') as f:
        content = f.read()
        [raw_mappings, target] = content.strip().split('\n\n')

        mappings = defaultdict(list)
        raw_mappings_lines = raw_mappings.strip().split('\n')

        for line in raw_mappings_lines:
            left, right = line.split(' => ')
            mappings[left.strip()].append(right.strip())

        return (mappings , target)

def process(file_name):
    entry_list, passw = parse(file_name)
    return solve(entry_list, passw)

def main():
    print(process('input.txt'))

if __name__ == '__main__':
    main()
