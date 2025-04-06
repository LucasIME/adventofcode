use std::fs;

fn parse_instruction(entry: isize) -> (isize, Vec<isize>) {
    let instruction = entry % 100;

    let modes_base = entry / 100;

    return (
        instruction,
        vec![
            modes_base % 10,
            (modes_base % 100) / 10,
            (modes_base % 1000) / 100,
        ],
    );
}

fn get_val(opcodes: &Vec<isize>, i: isize, mode: isize) -> isize {
    match mode {
        0 => {
            return opcodes[i as usize];
        }
        1 => {
            return i;
        }
        _ => println!("Unknown mode to get val!"),
    }
    return 99;
}

fn process_op_codes(opcodes: &mut Vec<isize>, input: isize, output: &mut Vec<isize>) {
    let mut cur_pos = 0;
    while cur_pos < opcodes.len() {
        let (instruction, modes) = parse_instruction(opcodes[cur_pos]);
        match instruction {
            1 => {
                let first_param = opcodes[cur_pos + 1];
                let second_param = opcodes[cur_pos + 2];
                let third_param = opcodes[cur_pos + 3];
                let sum = get_val(&opcodes, first_param, modes[0])
                    + get_val(&opcodes, second_param, modes[1]);
                let pos_to_change = third_param;
                opcodes[pos_to_change as usize] = sum;
                cur_pos += 4;
            }
            2 => {
                let first_param = opcodes[cur_pos + 1];
                let second_param = opcodes[cur_pos + 2];
                let third_param = opcodes[cur_pos + 3];
                let mul = get_val(&opcodes, first_param, modes[0])
                    * get_val(&opcodes, second_param, modes[1]);
                let pos_to_change = third_param;
                opcodes[pos_to_change as usize] = mul;
                cur_pos += 4;
            }
            3 => {
                let param = opcodes[cur_pos + 1];
                opcodes[param as usize] = input;
                cur_pos += 2;
            }
            4 => {
                let param = opcodes[cur_pos + 1];
                let val = get_val(&opcodes, param, modes[0]);

                output.push(val);
                cur_pos += 2;
            }
            99 => break,
            _ => println!("error!"),
        }
    }
}

fn parse(input: String) -> Vec<isize> {
    return input
        .split(",")
        .map(|x| x.parse::<isize>().unwrap())
        .collect();
}

pub fn part1() -> isize {
    let input = fs::read_to_string("resources/day05/day05.txt")
        .unwrap()
        .trim()
        .to_string();
    let mut op_array = parse(input);

    let input = 1;
    let mut output = vec![];

    process_op_codes(&mut op_array, input, &mut output);

    return output.last().unwrap().clone();
}

fn process_op_codes2(opcodes: &mut Vec<isize>, input: isize, output: &mut Vec<isize>) {
    let mut cur_pos = 0;
    while cur_pos < opcodes.len() {
        let (instruction, modes) = parse_instruction(opcodes[cur_pos]);
        match instruction {
            1 => {
                let first_param = opcodes[cur_pos + 1];
                let second_param = opcodes[cur_pos + 2];
                let third_param = opcodes[cur_pos + 3];
                let sum = get_val(&opcodes, first_param, modes[0])
                    + get_val(&opcodes, second_param, modes[1]);
                let pos_to_change = third_param;
                opcodes[pos_to_change as usize] = sum;
                cur_pos += 4;
            }
            2 => {
                let first_param = opcodes[cur_pos + 1];
                let second_param = opcodes[cur_pos + 2];
                let third_param = opcodes[cur_pos + 3];
                let mul = get_val(&opcodes, first_param, modes[0])
                    * get_val(&opcodes, second_param, modes[1]);
                let pos_to_change = third_param;
                opcodes[pos_to_change as usize] = mul;
                cur_pos += 4;
            }
            3 => {
                let param = opcodes[cur_pos + 1];
                opcodes[param as usize] = input;
                cur_pos += 2;
            }
            4 => {
                let param = opcodes[cur_pos + 1];
                let val = get_val(&opcodes, param, modes[0]);

                output.push(val);
                cur_pos += 2;
            }
            5 => {
                let first_param = get_val(&opcodes, opcodes[cur_pos + 1], modes[0]);
                if first_param != 0 {
                    let second_param = opcodes[cur_pos + 2];
                    cur_pos = get_val(&opcodes, second_param, modes[1]) as usize;
                } else {
                    cur_pos += 3;
                }
            }
            6 => {
                let first_param = get_val(&opcodes, opcodes[cur_pos + 1], modes[0]);
                if first_param == 0 {
                    let second_param = opcodes[cur_pos + 2];
                    cur_pos = get_val(&opcodes, second_param, modes[1]) as usize;
                } else {
                    cur_pos += 3;
                }
            }
            7 => {
                let first_param = opcodes[cur_pos + 1];
                let second_param = opcodes[cur_pos + 2];
                let third_param = opcodes[cur_pos + 3];

                let first_val = get_val(&opcodes, first_param, modes[0]);
                let second_val = get_val(&opcodes, second_param, modes[1]);
                if first_val < second_val {
                    opcodes[third_param as usize] = 1;
                } else {
                    opcodes[third_param as usize] = 0;
                }
                cur_pos += 4;
            }
            8 => {
                let first_param = opcodes[cur_pos + 1];
                let second_param = opcodes[cur_pos + 2];
                let third_param = opcodes[cur_pos + 3];

                let first_val = get_val(&opcodes, first_param, modes[0]);
                let second_val = get_val(&opcodes, second_param, modes[1]);
                if first_val == second_val {
                    opcodes[third_param as usize] = 1;
                } else {
                    opcodes[third_param as usize] = 0;
                }
                cur_pos += 4;
            }
            99 => break,
            _ => panic!("Error! Unknown instruction {}", instruction),
        }
    }
}

pub fn part2() -> isize {
    let input = fs::read_to_string("resources/day05/day05.txt")
        .unwrap()
        .trim()
        .to_string();
    let mut op_array = parse(input);

    let input = 5;
    let mut output = vec![];

    process_op_codes2(&mut op_array, input, &mut output);

    return output.last().unwrap().clone();
}
