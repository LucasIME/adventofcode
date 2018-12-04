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

def get_most_slept_min(guard, sleep_start, sleep_end):
    mins = defaultdict(int)
    for index in range(len(sleep_start[guard])):
        min_start = sleep_start[guard][index]
        min_end = sleep_end[guard][index]
        for minute in range(min_start, min_end):
            mins[minute] += 1
    return get_max_key(mins)

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

    most_sleep_guard = get_max_key(sleep_count)
    most_slept_min = get_most_slept_min(most_sleep_guard, sleep_start, sleep_end)

    return most_sleep_guard * most_slept_min

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

