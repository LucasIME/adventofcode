import sys
import re
from collections import defaultdict

start_pattern = r"Begin in state (.*)."
check_sum_pattern = r"Perform a diagnostic checksum after (.*) steps."
state_pattern = r"In state (.*):"
rule_pattern = r"If the current value is (.*):"
write_pattern = r"- Write the value (.*)."
move_pattern = r"- Move one slot to the (.*)."
continue_pattern = r"- Continue with state (.*)."

START = 'start'
CHECK_SUM = 'check_sum'
COND = 'cond'
ACTIONS = 'actions'
WRITE = 'write'
MOVE = 'move'
CONTINUE = 'continue'
LEFT = 'left'
RIGHT = 'right'

def parse_lines(lines: list[str], i: int, acc_state):
    if i >= len(lines):
        return acc_state

    if lines[i] == '':
        return parse_lines(lines, i+1, acc_state)

    line = lines[i].strip()

    if start := re.match(start_pattern, line):
        acc_state[START] = start.group(1)
        return parse_lines(lines, i+1, acc_state)
    elif check_str := re.match(check_sum_pattern, line):
        acc_state[CHECK_SUM] = int(check_str.group(1))
        return parse_lines(lines, i+1, acc_state)
    elif state_match := re.match(state_pattern, line):
        state = state_match.group(1)
        if state not in acc_state:
            acc_state[state] = []
        cur_line_index = i+1
        while rule_val_str := re.match(rule_pattern, lines[cur_line_index]):
            rule_val = int(rule_val_str.group(1))
            rule = { COND: rule_val, ACTIONS: []}
            cur_line_index += 1
            while cur_line_index < len(lines):
                if write_str := re.match(write_pattern, lines[cur_line_index]):
                    write = int(write_str.group(1))
                    rule[ACTIONS].append((WRITE, write))
                    cur_line_index += 1
                elif move_str := re.match(move_pattern, lines[cur_line_index]):
                    rule[ACTIONS].append((MOVE, move_str.group(1)))
                    cur_line_index += 1
                elif cont_state := re.match(continue_pattern, lines[cur_line_index]):
                    rule[ACTIONS].append((CONTINUE, cont_state.group(1)))
                    cur_line_index += 1
                else:
                    break
            acc_state[state].append(rule)
            if cur_line_index >= len(lines):
                break
        return parse_lines(lines, cur_line_index, acc_state)

    raise Exception("should never get here")

def parse(file_name: str):
    with open(file_name, 'r') as file:
        return parse_lines([line.strip() for line in file.readlines()], 0, {})

def part_1(file_name: str):
    parsed_input = parse(file_name)
    tape = defaultdict(int)
    cursor = 0
    state = parsed_input[START]
    rounds = parsed_input[CHECK_SUM]

    for round in range(rounds):
        state_rules = parsed_input[state]
        for rule in state_rules:
            rule_cond = rule[COND]
            if tape[cursor] == rule_cond:
                for (act, param) in rule[ACTIONS]:
                    if act == WRITE:
                        tape[cursor] = param
                    elif act == MOVE:
                        if param == LEFT:
                            cursor -= 1
                        elif param == RIGHT:
                            cursor += 1
                        else:
                            raise Exception("Non left/right param for move!")
                    elif act == CONTINUE:
                        state = param
                    else:
                        raise Exception("Unknown rule act!")
                break # No need to compare to other rules

    return len([v for (k, v) in tape.items() if v == 1])

if __name__ == '__main__':
    file_name = sys.argv[1]
    resp = part_1(file_name)
    print(resp)
