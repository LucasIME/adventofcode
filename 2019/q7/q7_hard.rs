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

struct Computer {
    input: VecDeque<isize>,
    output: Vec<isize>,
    memory: Vec<isize>,
    cur_pos: usize,
    last_intruction: usize,
}

impl Computer {
    fn process_next_op(&mut self) {
        let (instruction, modes) = parse_instruction(self.memory[self.cur_pos]);
        self.last_intruction = instruction as usize;
        match instruction {
            1 => {
                let first_param = self.memory[self.cur_pos + 1];
                let second_param = self.memory[self.cur_pos + 2];
                let third_param = self.memory[self.cur_pos + 3];
                let sum = get_val(&self.memory, first_param, modes[0])
                    + get_val(&self.memory, second_param, modes[1]);
                let pos_to_change = third_param;
                self.memory[pos_to_change as usize] = sum;
                self.cur_pos += 4;
            }
            2 => {
                let first_param = self.memory[self.cur_pos + 1];
                let second_param = self.memory[self.cur_pos + 2];
                let third_param = self.memory[self.cur_pos + 3];
                let mul = get_val(&self.memory, first_param, modes[0])
                    * get_val(&self.memory, second_param, modes[1]);
                let pos_to_change = third_param;
                self.memory[pos_to_change as usize] = mul;
                self.cur_pos += 4;
            }
            3 => {
                let param = self.memory[self.cur_pos + 1];
                self.memory[param as usize] = self.input.pop_front().unwrap();
                self.cur_pos += 2;
            }
            4 => {
                let param = self.memory[self.cur_pos + 1];
                let val = get_val(&self.memory, param, modes[0]);

                self.output.push(val);
                self.cur_pos += 2;
            }
            5 => {
                let first_param = get_val(&self.memory, self.memory[self.cur_pos + 1], modes[0]);
                if first_param != 0 {
                    let second_param = self.memory[self.cur_pos + 2];
                    self.cur_pos = get_val(&self.memory, second_param, modes[1]) as usize;
                } else {
                    self.cur_pos += 3;
                }
            }
            6 => {
                let first_param = get_val(&self.memory, self.memory[self.cur_pos + 1], modes[0]);
                if first_param == 0 {
                    let second_param = self.memory[self.cur_pos + 2];
                    self.cur_pos = get_val(&self.memory, second_param, modes[1]) as usize;
                } else {
                    self.cur_pos += 3;
                }
            }
            7 => {
                let first_param = self.memory[self.cur_pos + 1];
                let second_param = self.memory[self.cur_pos + 2];
                let third_param = self.memory[self.cur_pos + 3];

                let first_val = get_val(&self.memory, first_param, modes[0]);
                let second_val = get_val(&self.memory, second_param, modes[1]);
                if first_val < second_val {
                    self.memory[third_param as usize] = 1;
                } else {
                    self.memory[third_param as usize] = 0;
                }
                self.cur_pos += 4;
            }
            8 => {
                let first_param = self.memory[self.cur_pos + 1];
                let second_param = self.memory[self.cur_pos + 2];
                let third_param = self.memory[self.cur_pos + 3];

                let first_val = get_val(&self.memory, first_param, modes[0]);
                let second_val = get_val(&self.memory, second_param, modes[1]);
                if first_val == second_val {
                    self.memory[third_param as usize] = 1;
                } else {
                    self.memory[third_param as usize] = 0;
                }
                self.cur_pos += 4;
            }
            99 => {}
            _ => panic!("Error! Unknown instruction {}", instruction),
        }
    }

    fn process_until_output_or_break(&mut self) {
        self.process_next_op();
        if self.last_intruction == 99 || self.last_intruction == 4 {
            return;
        }

        self.process_until_output_or_break();
    }
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
    let amp_num: usize = 5;
    let phase = vec![5, 6, 7, 8, 9];
    let mut resp = 0;

    for perm in permutations(HashSet::from_iter(phase)) {
        let phase_perm_array = Vec::from_iter(perm);

        let mut amps: Vec<Computer> = (0..amp_num)
            .map(|i| {
                let mut amp_input = VecDeque::new();
                amp_input.push_back(phase_perm_array[i]);

                Computer {
                    memory: opcodes.clone(),
                    input: amp_input,
                    output: vec![],
                    cur_pos: 0,
                    last_intruction: 0,
                }
            })
            .collect();

        let mut input = 0;
        let mut cur_amp_index = 0;
        loop {
            let amp = &mut amps[cur_amp_index];
            amp.input.push_back(input);
            amp.process_until_output_or_break();

            if amp.last_intruction == 99 {
                break;
            }

            let amp_out = amp.output.last().unwrap();
            input = *amp_out;

            if cur_amp_index == amp_num - 1 {
                resp = std::cmp::max(resp, *amp_out);
            }

            cur_amp_index = (cur_amp_index + 1) % amp_num;
        }
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
