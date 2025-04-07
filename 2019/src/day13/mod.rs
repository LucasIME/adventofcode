use std::cmp::Ordering;
use std::collections::HashMap;
use std::collections::HashSet;
use std::collections::VecDeque;
use crate::intcode::Computer;

fn parse(input: String) -> HashMap<usize, isize> {
    return input
        .split(",")
        .enumerate()
        .map(|(i, x)| (i, x.parse::<isize>().unwrap()))
        .collect();
}

pub fn part1() -> usize {
    let input = std::fs::read_to_string("resources/day13/day13.txt")
        .unwrap()
        .trim()
        .to_string();
    let op_map = parse(input);

    let mut computer = Computer {
        input: VecDeque::new(),
        output: vec![],
        memory: op_map,
        cur_pos: 0,
        last_intruction: 0,
        relative_base_offset: 0,
    };

    let mut block_set = HashSet::new();

    loop {
        computer.process_until_break_or_out();
        if computer.last_intruction == 99 {
            break;
        }
        let x = computer.get_last_out();
        computer.process_until_break_or_out();
        let y = computer.get_last_out();
        computer.process_until_break_or_out();
        let tile_id = computer.output.last().unwrap();

        if *tile_id == 2 {
            block_set.insert((x, y));
        }
    }

    let resp = block_set.len();

    return resp;
}

pub fn part2() -> isize {
    let input = std::fs::read_to_string("resources/day13/day13.txt")
        .unwrap()
        .trim()
        .to_string();
    let mut op_map = parse(input);
    op_map.insert(0, 2);

    let mut computer = Computer {
        input: VecDeque::new(),
        output: vec![],
        memory: op_map,
        cur_pos: 0,
        last_intruction: 0,
        relative_base_offset: 0,
    };

    let mut score = 0;
    let mut paddle_pos = 0;

    loop {
        computer.process_until_break_or_out();
        if computer.last_intruction == 99 {
            break;
        }
        let x = computer.get_last_out();
        computer.process_until_break_or_out();
        let y = computer.get_last_out();
        computer.process_until_break_or_out();
        let tile_id = computer.get_last_out();

        match (x, y, tile_id) {
            (-1, 0, _) => score = tile_id,
            (_, _, 3) => paddle_pos = x,
            (_, _, 4) => {
                let ball_pos = x;
                let joystick = match ball_pos.cmp(&paddle_pos) {
                    Ordering::Greater => 1,
                    Ordering::Equal => 0,
                    Ordering::Less => -1,
                };

                computer.input.push_back(joystick);
            }
            _ => {}
        }
    }

    return score;
}
