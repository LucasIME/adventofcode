use crate::intcode::Computer;
use crate::intcode::parse_opcodes;
use std::collections::VecDeque;

pub fn part1() -> i32 {
    let input = std::fs::read_to_string("resources/day19/day19.txt")
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

    let mut affected = 0;
    for row in 0..50 {
        for col in 0..50 {
            let mut query_pc = computer.clone();
            query_pc.input.push_back(col);
            query_pc.input.push_back(row);
            query_pc.process_until_break_or_out();
            let out = query_pc.get_last_out();
            if out == 1 {
                affected += 1;
            }
        }
    }

    return affected;
}

pub fn part2() -> i32 {
    let input = std::fs::read_to_string("resources/day19/day19.txt")
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

    let mut cur= (4, 3);
    let size = 100;

    loop {
        if check_square(&computer, cur, size) {
            let top_left = (cur.0, cur.1 - size + 1);
            return 10_000 * top_left.1 + top_left.0;
        }

        cur = get_next_pos(&computer, cur);
    }
}

fn get_next_pos(computer: &Computer, cur: (i32, i32)) -> (i32, i32) {
    for row in cur.0..=cur.0 + 5 {
        for col in cur.1..=cur.1 + 5 {
            if (row, col) == cur {
                continue;
            }

            if check_pos_valid(computer, (row, col)) {
                return (row, col);
            }
        }
    }

    panic!("Couldn't find next pos");
}

fn check_square(computer: &Computer, top_right_pos: (i32, i32), size: i32) -> bool {
    let top_left_pos = (top_right_pos.0, top_right_pos.1 - size + 1);
    let bot_right_pos = (top_right_pos.0 + size - 1, top_right_pos.1);
    let bot_left_pos = (top_right_pos.0 + size - 1, top_right_pos.1 - size + 1);

    let top_left_valid = check_pos_valid(computer, top_left_pos);
    let bot_right_valid = check_pos_valid(computer, bot_right_pos);
    let bot_left_valid = check_pos_valid(computer, bot_left_pos);
    return top_left_valid &&  bot_right_valid && bot_left_valid;
}

fn check_pos_valid(computer: &Computer, pos: (i32, i32)) -> bool {
    let mut query_pc = computer.clone();
    query_pc.input.push_back(pos.1 as isize);
    query_pc.input.push_back(pos.0 as isize);
    query_pc.process_until_break_or_out();
    let out = query_pc.get_last_out();
    return out == 1;
}
