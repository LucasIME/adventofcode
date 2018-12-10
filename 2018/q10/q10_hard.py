from collections import defaultdict
import re

def parse_line(line):
    x, y, vx, vy = [int(i) for i in re.findall(r'-?\d+', line)]
    return {'x': x, 'y': y, 'vx': vx, 'vy': vy}

def parse(entry_list):
    return [parse_line(line) for line in entry_list]

def normalize(nodes):
    minX = float('inf')
    minY = float('inf')
    maxX = float('-inf')
    maxY = float('-inf')
    for node in nodes:
        minX = min(minX, node['x'])
        minY = min(minY, node['y'])
        maxX = max(maxX, node['x'])
        maxY = max(maxY, node['y'])

    for node in nodes:
        node['x'] -= minX
        node['y'] -= minY

    width = maxX - minX + 1
    height = maxY - minY + 1

    return nodes, width, height

def to_matrix(nodes, width, height):
    matrix = [[' ' for i in range(width)] for j in range(height)]
    for node in nodes:
        row = node['y']
        col = node['x']
        matrix[row][col] = '#'
    return matrix


def print_matrix(matrix):
    for line in matrix:
        print(''.join(line))

def is_reasonable_size(width, height):
    return width < 200 and height < 11

def process(entry_list):
    nodes = parse(entry_list)
    time = 0
    while True:
        time += 1
        for i, node in enumerate(nodes):
            node['x'] += node['vx']
            node['y'] += node['vy']
        normalized_nodes, width, height = normalize(nodes)
        if is_reasonable_size(width, height):
            print_matrix(to_matrix(nodes, width, height))
            return time

def main():
    entry_list = []
    while True:
       line = input()
       if line == '':
           break
       entry_list.append(line)
    resp = process(entry_list)
    print(resp)

if __name__ == '__main__':
    main()

