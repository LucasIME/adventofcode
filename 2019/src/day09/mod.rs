use crate::intcode::{Computer, parse_opcodes};
use std::collections::VecDeque;

pub fn part1() -> isize {
    let input = std::fs::read_to_string("resources/day09/day09.txt")
        .unwrap()
        .trim()
        .to_string();
    let op_map = parse_opcodes(input);

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
    let op_map = parse_opcodes(input);

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
