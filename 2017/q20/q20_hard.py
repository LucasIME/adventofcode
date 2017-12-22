import re
from collections import defaultdict

pattern = re.compile('p=<(.*)>, v=<(.*)>, a=<(.*)>')

def process(line):
    match = pattern.match(line)
    p = [int(x) for x in match.group(1).split(',')]
    v = [int(x) for x in match.group(2).split(',')]
    a = [int(x) for x in match.group(3).split(',')]
    return p, v, a

def sum_dims(v1, v2):
    return (v1[0] + v2[0], v1[1] + v2[1], v1[2] + v2[2])

def get_next_world(world):
    new_w = defaultdict(list)
    for position in world:
        if len(world[position]) > 1:
            continue
        v, a = world[position][0]
        v = sum_dims(v, a)
        new_pos =  sum_dims(position, v)
        new_w[new_pos].append((v,a))
    return new_w

def get_particles_left(entry):
    world = defaultdict(list)
    i = 0
    last_count = 0
    for particle in entry:
        p, v, a = particle
        p, v, a = tuple(p), tuple(v), tuple(a)
        world[p].append((v, a))

    #assuming that if world doesn't change in 100 steps, it won't change anymore
    while i < 100:
        world = get_next_world(world)
        if len(world) != last_count:
            last_count = len(world)
            i = 0
        i += 1
    return len(world)

def main():
    entry = []
    while True:
        line = input()
        if line == '':
            break
        line = process(line)
        entry.append(line)
    resp = get_particles_left(entry)
    print(resp)

if __name__ == '__main__':
    main()
