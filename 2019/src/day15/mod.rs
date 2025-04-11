use crate::intcode::Computer;
use crate::intcode::parse_opcodes;
use std::collections::HashMap;
use std::collections::HashSet;
use std::collections::VecDeque;

fn dfs(computer: Computer, target: isize) -> i32 {
    let mut queue: VecDeque<((i32, i32), i32, Computer)> = VecDeque::new();
    let start = (0, 0);
    let mut visited: HashSet<(i32, i32)> = HashSet::new();

    queue.push_back((start, 0, computer.clone()));

    let direction_ops = [1, 2, 3, 4];
    let direction_vecs: HashMap<isize, (i32, i32)> =
        HashMap::from_iter([(1, (-1, 0)), (2, (1, 0)), (3, (0, -1)), (4, (0, 1))]);

    while !queue.is_empty() {
        let (cur, dist, comp) = queue.pop_front().unwrap();

        if visited.contains(&cur) {
            continue;
        }

        visited.insert(cur);

        for direction in direction_ops {
            let dir_vec = direction_vecs[&direction];
            let new_pos = (cur.0 + dir_vec.0, cur.1 + dir_vec.1);

            let mut new_input = comp.input.clone();
            new_input.push_back(direction);
            let mut new_comp = comp.clone();
            new_comp.input = new_input;

            new_comp.process_until_break_or_out();
            let out = new_comp.get_last_out();

            if out == target {
                return dist + 1;
            }

            if out == 1 {
                queue.push_back((new_pos, dist + 1, new_comp.clone()));
            }
        }
    }

    return -1;
}

pub fn part1() -> i32 {
    let input = std::fs::read_to_string("resources/day15/day15.txt")
        .unwrap()
        .trim()
        .to_string();
    let op_map = parse_opcodes(input);

    let computer = Computer {
        input: VecDeque::new(),
        output: vec![],
        memory: op_map,
        cur_pos: 0,
        last_intruction: 0,
        relative_base_offset: 0,
    };

    let resp = dfs(computer, 2);

    return resp;
}

pub fn part2() -> i32 {
    let input = std::fs::read_to_string("resources/day15/day15.txt")
        .unwrap()
        .trim()
        .to_string();
    let op_map = parse_opcodes(input);

    let computer = Computer {
        input: VecDeque::new(),
        output: vec![],
        memory: op_map,
        cur_pos: 0,
        last_intruction: 0,
        relative_base_offset: 0,
    };

    let (map, target_pos) = get_map_and_target_coords(computer.clone(), 2);
    return flood_fill(map, target_pos);
}

fn get_map_and_target_coords(
    computer: Computer,
    target: isize,
) -> (HashSet<(i32, i32)>, (i32, i32)) {
    let mut queue: VecDeque<((i32, i32), i32, Computer)> = VecDeque::new();
    let start = (0, 0);
    let mut visited: HashSet<(i32, i32)> = HashSet::new();

    queue.push_back((start, 0, computer.clone()));

    let direction_ops = [1, 2, 3, 4];
    let direction_vecs: HashMap<isize, (i32, i32)> =
        HashMap::from_iter([(1, (-1, 0)), (2, (1, 0)), (3, (0, -1)), (4, (0, 1))]);

    let mut target_pos = (-1, -1);

    while !queue.is_empty() {
        let (cur, dist, comp) = queue.pop_front().unwrap();

        if visited.contains(&cur) {
            continue;
        }

        visited.insert(cur);

        for direction in direction_ops {
            let dir_vec = direction_vecs[&direction];
            let new_pos = (cur.0 + dir_vec.0, cur.1 + dir_vec.1);

            let mut new_input = comp.input.clone();
            new_input.push_back(direction);
            let mut new_comp = comp.clone();
            new_comp.input = new_input;

            new_comp.process_until_break_or_out();
            let out = new_comp.get_last_out();

            if out == target {
                target_pos = new_pos;
            }

            if out == 1 {
                queue.push_back((new_pos, dist + 1, new_comp.clone()));
            }
        }
    }

    return (visited, target_pos);
}

fn flood_fill(map: HashSet<(i32, i32)>, start_pos: (i32, i32)) -> i32 {
    let mut visited: HashSet<(i32, i32)> = HashSet::new();

    let mut queue: VecDeque<(i32, i32)> = VecDeque::new();
    queue.push_back(start_pos);

    let mut rounds = 0;
    while visited.len() < map.len() {
        let mut new_queue: VecDeque<(i32, i32)> = VecDeque::new();

        while !queue.is_empty() {
            let cur = queue.pop_front().unwrap();

            if visited.contains(&cur) {
                continue;
            }

            visited.insert(cur);

            let direction_vecs = vec![(1, 0), (-1, 0), (0, 1), (0, -1)];

            for dir_vec in direction_vecs {
                let new_pos = (cur.0 + dir_vec.0, cur.1 + dir_vec.1);
                if map.contains(&new_pos) && !visited.contains(&new_pos) {
                    new_queue.push_back(new_pos);
                }
            }
        }

        queue = new_queue;
        rounds += 1
    }

    return rounds;
}
