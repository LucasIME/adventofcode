use std::collections::HashMap;
use std::collections::VecDeque;
use std::io;
use std::io::BufRead;

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

#[derive(Debug)]
struct Computer {
    input: VecDeque<isize>,
    output: Vec<isize>,
    memory: HashMap<usize, isize>,
    cur_pos: usize,
    last_intruction: usize,
    relative_base_offset: isize,
}

impl Computer {
    fn get_mem_address(&mut self, address: usize) -> isize {
        return *self.memory.entry(address).or_insert(0);
    }

    fn get_val(&mut self, address: isize, mode: isize) -> isize {
        match mode {
            0 => {
                return self.get_mem_address(address as usize);
            }
            1 => {
                return address;
            }
            2 => {
                return self.get_mem_address((address + self.relative_base_offset) as usize);
            }
            _ => panic!("Unknown mode to get val!"),
        }
    }

    fn write_to_address(&mut self, address: usize, val: isize, mode: isize) {
        let final_address;
        match mode {
            0 => final_address = address,
            1 => panic!("write with Immediate mode!"),
            2 => final_address = (self.relative_base_offset + address as isize) as usize, // We have to convert address to isize first or we can overflow.
            _ => panic!("Non expected mode!"),
        }

        self.memory.insert(final_address, val);
    }

    fn process_next_op(&mut self) {
        let (instruction, modes) = parse_instruction(self.get_mem_address(self.cur_pos));
        self.last_intruction = instruction as usize;
        match instruction {
            1 => {
                let first_param = self.get_mem_address(self.cur_pos + 1);
                let second_param = self.get_mem_address(self.cur_pos + 2);
                let third_param = self.get_mem_address(self.cur_pos + 3);
                let sum =
                    self.get_val(first_param, modes[0]) + self.get_val(second_param, modes[1]);
                let pos_to_change = third_param;

                self.write_to_address(pos_to_change as usize, sum, modes[2]);
                self.cur_pos += 4;
            }
            2 => {
                let first_param = self.get_mem_address(self.cur_pos + 1);
                let second_param = self.get_mem_address(self.cur_pos + 2);
                let third_param = self.get_mem_address(self.cur_pos + 3);
                let mul =
                    self.get_val(first_param, modes[0]) * self.get_val(second_param, modes[1]);
                let pos_to_change = third_param;

                self.write_to_address(pos_to_change as usize, mul, modes[2]);
                self.cur_pos += 4;
            }
            3 => {
                let input_val = self.input.pop_front().unwrap();
                let pos_to_change = self.get_mem_address(self.cur_pos + 1);

                self.write_to_address(pos_to_change as usize, input_val, modes[0]);

                self.cur_pos += 2;
            }
            4 => {
                let param = self.get_mem_address(self.cur_pos + 1);
                let val = self.get_val(param, modes[0]);

                self.output.push(val);
                self.cur_pos += 2;
            }
            5 => {
                let first_param = self.get_mem_address(self.cur_pos + 1);
                let first_val = self.get_val(first_param, modes[0]);

                let second_param = self.get_mem_address(self.cur_pos + 2);
                let second_val = self.get_val(second_param, modes[1]);

                if first_val != 0 {
                    self.cur_pos = second_val as usize;
                } else {
                    self.cur_pos += 3;
                }
            }
            6 => {
                let first_param = self.get_mem_address(self.cur_pos + 1);
                let first_val = self.get_val(first_param, modes[0]);

                let second_param = self.get_mem_address(self.cur_pos + 2);
                let second_val = self.get_val(second_param, modes[1]);

                if first_val == 0 {
                    self.cur_pos = second_val as usize;
                } else {
                    self.cur_pos += 3;
                }
            }
            7 => {
                let first_param = self.get_mem_address(self.cur_pos + 1);
                let second_param = self.get_mem_address(self.cur_pos + 2);
                let third_param = self.get_mem_address(self.cur_pos + 3);

                let first_val = self.get_val(first_param, modes[0]);
                let second_val = self.get_val(second_param, modes[1]);

                if first_val < second_val {
                    self.write_to_address(third_param as usize, 1, modes[2]);
                } else {
                    self.write_to_address(third_param as usize, 0, modes[2]);
                }
                self.cur_pos += 4;
            }
            8 => {
                let first_param = self.get_mem_address(self.cur_pos + 1);
                let second_param = self.get_mem_address(self.cur_pos + 2);
                let third_param = self.get_mem_address(self.cur_pos + 3);

                let first_val = self.get_val(first_param, modes[0]);
                let second_val = self.get_val(second_param, modes[1]);

                if first_val == second_val {
                    self.write_to_address(third_param as usize, 1, modes[2]);
                } else {
                    self.write_to_address(third_param as usize, 0, modes[2]);
                }
                self.cur_pos += 4;
            }
            9 => {
                let first_param = self.get_mem_address(self.cur_pos + 1);
                let first_val = self.get_val(first_param, modes[0]);

                self.relative_base_offset += first_val;
                self.cur_pos += 2;
            }
            99 => {}
            _ => panic!("Error! Unknown instruction {}", instruction),
        }
    }

    fn process_until_break_or_output(&mut self) {
        self.process_next_op();
        loop {
            if self.last_intruction == 99 || self.last_intruction == 4 {
                break;
            }
            self.process_next_op();
        }
    }
}

fn parse(input: String) -> HashMap<usize, isize> {
    return input
        .split(",")
        .enumerate()
        .map(|(i, x)| (i, x.parse::<isize>().unwrap()))
        .collect();
}

fn remainder(a: isize, b: isize) -> isize {
    let base = a % b;
    if base < 0 {
        return base + b;
    }

    return base;
}

fn change_dir(cur_dir: isize, direction_to_turn: isize) -> isize {
    let mut new_dir_to_turn = direction_to_turn;
    if direction_to_turn == 0 {
        new_dir_to_turn = -1;
    }
    return remainder(cur_dir + new_dir_to_turn, 4);
}

fn change_pos(cur_pos: (isize, isize), direction: isize) -> (isize, isize) {
    let (x, y) = cur_pos;
    match direction {
        0 => (x, y + 1),
        1 => (x + 1, y),
        2 => (x, y - 1),
        3 => (x - 1, y),
        _ => panic!("Unknown direction to go!"),
    }
}

fn main() {
    let input = read_line();
    let op_map = parse(input);

    let mut computer = Computer {
        input: (0..1).collect::<VecDeque<_>>(),
        output: vec![],
        memory: op_map,
        cur_pos: 0,
        last_intruction: 0,
        relative_base_offset: 0,
    };

    let mut cur_dir = 0; // 0 = up, 1 = right, 2 = down, 3 = left
    let mut cur_pos = (0, 0);
    let mut colored_set = HashMap::new();

    loop {
        computer.process_until_break_or_output();
        if computer.last_intruction == 99 {
            break;
        }

        let color_to_paint = computer.output.last().unwrap();
        colored_set.insert(cur_pos, *color_to_paint);

        computer.process_until_break_or_output();
        let dir_to_turn = computer.output.last().unwrap();
        cur_dir = change_dir(cur_dir, *dir_to_turn);
        cur_pos = change_pos(cur_pos, cur_dir);

        computer
            .input
            .push_back(*colored_set.get(&cur_pos).unwrap_or(&0));
    }

    let resp = colored_set.len();

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
