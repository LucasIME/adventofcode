def parse(entry_list):
    d = {}
    for entry in entry_list:
        s = entry.split()
        racer = s[0]
        speed = int(s[3])
        speed_time = int(s[6])
        wait_time = int(s[-2])
        d[racer] = {'speed':speed, 'speed_time':speed_time, 'wait':wait_time}
    return d

def get_cur_speed(racers, racer_name, time):
    cur_racer = racers[racer_name]
    if time%(cur_racer['speed_time'] + cur_racer['wait']) < cur_racer['speed_time']:
        return cur_racer['speed']

    return 0

def get_cur_winners(state):
    max_dist = state[max(state, key=state.get)]
    return [racer_name for racer_name, dist in state.items() if dist == max_dist]

def process_entry(entry_list):
    racers = parse(entry_list)
    state = {racer:0 for racer in racers}
    points = {racer:0 for racer in racers}
    for time in range(2503):
        for racer in racers:
            state[racer] += get_cur_speed(racers, racer, time)
        for winner in get_cur_winners(state):
            points[winner] += 1
    print(state, points)
    return points[max(points, key=points.get)]

def main():
    entry_list = []
    while True:
        entry = input()
        if entry == '':
            break
        entry_list.append(entry)
    resp = process_entry(entry_list)
    print(resp)

if __name__ == '__main__':
    main()
