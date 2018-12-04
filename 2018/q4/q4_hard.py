from collections import defaultdict

def parse_line(line):
    time_raw, action = line[1:].split('] ')
    return (time_raw, action)

def parse(entry_list):
    return [parse_line(line) for line in entry_list]

def get_minute(raw_time):
    return int(raw_time.split(':')[1])

def get_id(action):
    splits = action.split(' ')
    return int(splits[1][1:])

def get_max_key(d):
    resp = None
    maxi = float('-inf')
    for key, v in d.items():
        if v > maxi:
            maxi = v
            resp = key
    return resp

def process_action(time, action, sleep_start, sleep_end, sleep_count, last_guard):
    if action.startswith('Guard'):
        return get_id(action)
    if action == 'wakes up':
        sleep_end[last_guard].append(time)
        time_sleeping = time - sleep_start[last_guard][-1]
        sleep_count[last_guard] += time_sleeping
    if action == 'falls asleep':
        sleep_start[last_guard].append(time)
    return last_guard

def build_min_dict(guard, sleep_start, sleep_end):
    minutes = defaultdict(int)
    for index in range(len(sleep_start[guard])):
        min_start = sleep_start[guard][index]
        min_end = sleep_end[guard][index]
        for minute in range(min_start, min_end):
            minutes[minute] += 1
    return minutes

def get_guard_with_most_repeated_minutes(sleep_start, sleep_end):
    min_dicts = {}
    chosen_guard = None
    chosen_minute = None
    most_rep = float('-inf')
    for guard in sleep_start:
        min_dicts[guard] = build_min_dict(guard, sleep_start, sleep_end)
    for guard, minute_counts in min_dicts.items():
        most_rep_min = get_max_key(minute_counts)
        rep_num = minute_counts[most_rep_min]
        if rep_num > most_rep:
            most_rep = rep_num
            chosen_guard = guard
            chosen_minute = most_rep_min
    return chosen_guard, chosen_minute

def process(entry_list):
    entry_list = parse(entry_list)
    entry_list.sort()
    last_guard = None
    sleep_count = defaultdict(int)
    sleep_start= defaultdict(list)
    sleep_end = defaultdict(list)
    for line in entry_list:
        raw_time, action = line
        minute = get_minute(raw_time)
        last_guard = process_action(minute, action, sleep_start, sleep_end, sleep_count, last_guard)

    guard, minute = get_guard_with_most_repeated_minutes(sleep_start, sleep_end)

    return guard * minute

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

