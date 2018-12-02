from collections import defaultdict

def count_diff(w1, w2):
    resp = 0
    for i in range(len(w1)):
        if w1[i] != w2[i]:
            resp += 1
    return resp

def get_common(w1, w2):
    resp = []
    for i in range(len(w1)):
        if w1[i] == w2[i]:
            resp.append(w1[i])
    return ''.join(resp)

def process(entry_list):
    mini = float('inf')
    mini_index = None
    mini_index2 = None
    for i, entry in enumerate(entry_list):
        for j in range(i+1, len(entry_list)):
            entry2 = entry_list[j]
            diff = count_diff(entry, entry2)
            if diff < mini:
                mini = diff
                mini_index = i
                mini_index2 = j
    return get_common(entry_list[mini_index], entry_list[mini_index2])

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

