use std::collections::HashMap;
use std::collections::VecDeque;
use crate::intcode::Computer;

fn parse(input: String) -> HashMap<usize, isize> {
    return input
        .split(",")
        .enumerate()
        .map(|(i, x)| (i, x.parse::<isize>().unwrap()))
        .collect();
}

pub fn part1() -> isize {
    let input = std::fs::read_to_string("resources/day09/day09.txt")
        .unwrap()
        .trim()
        .to_string();
    let op_map = parse(input);

    let mut computer = Computer {
        input: (1..2).collect::<VecDeque<_>>(),
        output: vec![],
        memory: op_map,
        cur_pos: 0,
        last_intruction: 0,
        relative_base_offset: 0,
    };

    computer.process_until_break();

    let resp = computer.output;
    return resp.last().unwrap().clone();
}

pub fn part2() -> isize {
    let input = std::fs::read_to_string("resources/day09/day09.txt")
        .unwrap()
        .trim()
        .to_string();
    let op_map = parse(input);

    let mut computer = Computer {
        input: (2..3).collect::<VecDeque<_>>(),
        output: vec![],
        memory: op_map,
        cur_pos: 0,
        last_intruction: 0,
        relative_base_offset: 0,
    };

    computer.process_until_break();

    let resp = computer.output;
    return resp.last().unwrap().clone();
}
