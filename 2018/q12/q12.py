def parse_initial_state(entry):
    state = {}
    for i, plant in enumerate(entry.split('initial state: ')[1]):
        state[i] = plant
    return state

def parse(entry_list):
    initial_state = parse_initial_state(entry_list[0])
    rules = {}
    for entry in entry_list[1:]:
        pattern, result = entry.split(' => ')
        rules[pattern] = result
    return initial_state, rules

def get_plant(state, i):
    if i in state:
        return state[i]
    return '.'

def match_pattern(state, i, pattern):
    for diff in range(-2, +3):
        if get_plant(state, i + diff) != pattern[diff + 2]:
            return False
    return True

def advance_state(state, rules):
    new_state = {}
    for i, plant in state.items():
        for diff in range(-2, 3):
            for pattern, result in rules.items():
                if match_pattern(state, i + diff, pattern):
                    if result == '#':
                        new_state[i + diff] = result
                    break

    return new_state

def process(entry_list):
    initial_state, rules = parse(entry_list)
    state = initial_state
    for i in range(20):
        state = advance_state(state, rules)
    return sum(state.keys())

def main():
    entry_list = []
    blank_count = 0
    while True:
       line = input()
       if line == '' :
           if blank_count == 1:
               break
           else:
               blank_count += 1
       else:
           entry_list.append(line)
    resp = process(entry_list)
    print(resp)

if __name__ == '__main__':
    main()

