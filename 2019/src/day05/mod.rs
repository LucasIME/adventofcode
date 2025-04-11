use crate::intcode::{Computer, parse_opcodes};
use std::{collections::VecDeque, fs};

pub fn part1() -> isize {
    let input = fs::read_to_string("resources/day05/day05.txt")
        .unwrap()
        .trim()
        .to_string();
    let ops = parse_opcodes(input);

    let mut computer = Computer {
        input: (1..2).collect::<VecDeque<_>>(),
        output: vec![],
        memory: ops,
        cur_pos: 0,
        last_intruction: 0,
        relative_base_offset: 0,
    };

    computer.process_until_break();

    return computer.get_last_out();
}

pub fn part2() -> isize {
    let input = fs::read_to_string("resources/day05/day05.txt")
        .unwrap()
        .trim()
        .to_string();

    let ops = parse_opcodes(input);

    let mut computer = Computer {
        input: (5..6).collect::<VecDeque<_>>(),
        output: vec![],
        memory: ops,
        cur_pos: 0,
        last_intruction: 0,
        relative_base_offset: 0,
    };

    computer.process_until_break();

    return computer.get_last_out();
}
