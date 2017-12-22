import re
from math import sqrt

pattern = re.compile('p=<(.*)>, v=<(.*)>, a=<(.*)>')

def process(line):
    match = pattern.match(line)
    p = [int(x) for x in match.group(1).split(',')]
    v = [int(x) for x in match.group(2).split(',')]
    a = [int(x) for x in match.group(3).split(',')]
    return p, v, a

def get_modulus(a):
    return abs(a[0]) + abs(a[1]) + abs(a[2])

def get_closest_particle(entry):
    min_a = float('inf')
    resp = 0
    for i, particle in enumerate(entry):
        p, v, a = particle
        mod_a = get_modulus(a)
        print(p, v, a, mod_a, i)
        if mod_a < min_a:
            print(particle, mod_a, i)
            min_a = mod_a
            resp = i
        elif mod_a == min_a:
            if get_modulus(v) < get_modulus(entry[resp][1]):
                min_a = mod_a
                resp = i
    return resp

def main():
    entry = []
    while True:
        line = input()
        if line == '':
            break
        line = process(line)
        entry.append(line)
    resp = get_closest_particle(entry)
    print(resp)

if __name__ == '__main__':
    main()
