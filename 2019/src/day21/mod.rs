use crate::intcode::Computer;
use crate::intcode::parse_opcodes;
use std::collections::VecDeque;

fn print_out(computer: &mut Computer) {
    for entry in computer.output.iter() {
        print!("{}", *entry as u8 as char);
    }
}

pub fn part1() -> isize {
    let input = std::fs::read_to_string("resources/day21/day21.txt")
        .unwrap()
        .trim()
        .to_string();
    let op_map = parse_opcodes(input);

    let instructions = vec![
        "NOT B J",
        "NOT C T",
        "OR T J",
        "AND D J",
        "NOT A T",
        "OR T J",
        "WALK",
        "",];
    let instructions_ascii : VecDeque<isize> = instructions
        .join("\n")
        .chars()
        .map(|c| c as isize)
        .collect();

    let mut computer = Computer {
        input: instructions_ascii,
        output: vec![],
        memory: op_map,
        cur_pos: 0,
        last_intruction: 0,
        relative_base_offset: 0,
    };

    while computer.last_intruction != 99 {
        computer.process_until_break_or_out();
    }

    print_out(&mut computer);
    return computer.get_last_out();
}