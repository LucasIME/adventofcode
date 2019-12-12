use std::collections::HashSet;
use std::collections::VecDeque;
use std::io;
use std::io::BufRead;
use std::iter::FromIterator;

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
        _ => panic!("Unknown mode to get val!"),
    }
}

fn process_op_codes(opcodes: &mut Vec<isize>, mut input: VecDeque<isize>, output: &mut Vec<isize>) {
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
                opcodes[param as usize] = input.pop_front().unwrap();
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

fn get_amp_output(opcode: &Vec<isize>, input: isize, phase: isize) -> isize {
    let mut program_copy = opcode.clone();
    let mut real_input = VecDeque::new();
    real_input.push_back(phase);
    real_input.push_back(input);

    let mut output: Vec<isize> = vec![];
    process_op_codes(&mut program_copy, real_input, &mut output);

    return *output.last().unwrap();
}

fn permutations(collection: HashSet<isize>) -> Vec<HashSet<isize>> {
    if collection.len() == 1 {
        return vec![collection.clone()];
    }

    let mut output = Vec::new();

    for val in &collection {
        let mut my_set = HashSet::new();
        my_set.insert(*val);

        let remaining = collection.difference(&my_set).map(|x| *x).collect();
        let rem_perms = permutations(remaining);

        for perm in rem_perms {
            output.push(my_set.union(&perm).map(|x| *x).collect());
        }
    }

    return output;
}

fn find_max_thruster(opcodes: &mut Vec<isize>) -> isize {
    let amp_num = 5;
    let phase = vec![0, 1, 2, 3, 4];
    let mut resp = 0;

    for perm in permutations(HashSet::from_iter(phase)) {
        let perm_array = Vec::from_iter(perm);
        let mut input = 0;

        for amp in 0..amp_num {
            let amp_out = get_amp_output(&opcodes, input, perm_array[amp]);
            input = amp_out;
        }

        resp = std::cmp::max(resp, input);
    }

    return resp;
}

fn parse(input: String) -> Vec<isize> {
    return input
        .split(",")
        .map(|x| x.parse::<isize>().unwrap())
        .collect();
}

fn main() {
    let input = read_line();
    let mut op_array = parse(input);

    let resp = find_max_thruster(&mut op_array);

    println!("{:?}", resp);
}

fn read_line() -> String {
    return io::stdin()
        .lock()
        .lines()
        .map(|res| res.ok())
        .filter(|x| x.is_some())
        .map(|x| x.unwrap())
        .next()
        .unwrap();
}
