from collections import defaultdict

def get_initial_state(entry_list):
    d = defaultdict(list) 
    for phrase in entry_list:
        if phrase[0] == 'value':
            robot = int(phrase[-1])
            value = int(phrase[1])
            d[robot].append(value)
    return d

def get_rules(entry_list):
    low = {}
    high = {}
    for phrase in entry_list:
        if phrase[0] == 'bot':
            source = int(phrase[1])
            low_target = int(phrase[6])
            if phrase[5] == 'output':
                low_target = -1
            high_target = int(phrase[-1])
            if phrase[-2] == 'output':
                high_target = -1
            low[source] = low_target
            high[source] = high_target
    return low, high

def get_first_bot(state):
    for key, value in state.items():
        if len(value) == 2:
            return key

def process(entry_list):
    state = get_initial_state(entry_list)
    low, high = get_rules(entry_list)
    first = get_first_bot(state)
    q = [first]
    while q:
        cur = q.pop()
        mini = min(state[cur])
        maxi = max(state[cur])
        if mini == 17 and maxi == 61:
            return cur
        state[cur] = []
        state[low[cur]].append(mini)
        state[high[cur]].append(maxi)
        if len(state[low[cur]]) >= 2:
            q.append(low[cur])
        if len(state[high[cur]]) >= 2:
            q.append(high[cur])

def main():
    entry_list = []
    while True:
        entry = input()
        if entry == '':
            break
        entry_list.append(entry.split())
    resp = process(entry_list)
    print(resp)

if __name__ == '__main__':
    main()
