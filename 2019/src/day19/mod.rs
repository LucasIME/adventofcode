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

pub fn part1() -> i32 {
    let input = std::fs::read_to_string("resources/day19/day19.txt")
        .unwrap()
        .trim()
        .to_string();
    let op_map = parse(input);

    let computer = Computer {
        input: VecDeque::new(),
        output: vec![],
        memory: op_map,
        cur_pos: 0,
        last_intruction: 0,
        relative_base_offset: 0,
    };

    let mut affected = 0;
    for row in 0..50 {
        for col in 0..50 {
            let mut query_pc = computer.clone();
            query_pc.input.push_back(row);
            query_pc.input.push_back(col);
            query_pc.process_until_break_or_out();
            let out = query_pc.get_last_out();
            if out == 1 {
                affected += 1;
            }
        }
    }

    return affected;
}
