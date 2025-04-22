use crate::intcode::Computer;
use crate::intcode::parse_opcodes;
use std::collections::HashSet;

pub fn part1() -> isize {
    let input = std::fs::read_to_string("resources/day23/day23.txt")
        .unwrap()
        .trim()
        .to_string();
    let op_map = parse_opcodes(input);

    let mut computers = (0..50)
        .map(|i| Computer {
            input: vec![i].into_iter().collect(),
            output: vec![],
            memory: op_map.clone(),
            cur_pos: 0,
            last_intruction: 0,
            relative_base_offset: 0,
        })
        .collect::<Vec<_>>();

    loop {
        let mut pushed_comps: HashSet<usize> = HashSet::new();

        for i in 0..50 {
            let computer = &mut computers[i];

            computer.process_until_break_or_out();
            let maybe_address = computer.get_maybe_out();
            if maybe_address.is_none() {
                continue;
            }
            let address = maybe_address.unwrap();

            computer.process_until_break_or_out();
            let x = computer.get_last_out();
            computer.process_until_break_or_out();
            let y = computer.get_last_out();

            if address == 255 {
                return y;
            } else if address < 50 && address >= 0 {
                pushed_comps.insert(address as usize);
                computers[address as usize].input.push_back(x);
                computers[address as usize].input.push_back(y);
            }
        }

        for i in 0..50 {
            if !pushed_comps.contains(&i) {
                computers[i].input.push_back(-1);
            }
        }
    }
}

struct Nat {
    packet: (isize, isize),
    last_y: isize,
}

impl Nat {
    fn new() -> Nat {
        return Nat {
            packet: (0, 0),
            last_y: -12345,
        };
    }

    fn process_packet(&mut self, new_packet: (isize, isize)) {
        self.last_y = self.packet.1;
        self.packet = new_packet;
    }

    fn send_packet(&mut self, network: &mut Vec<Computer>) {
        network[0].input.clear();
        network[0].input.push_back(self.packet.0);
        network[0].input.push_back(self.packet.1);
    }

    fn is_duplicate(&self) -> bool {
        return self.packet.1 == self.last_y;
    }
}

pub fn part2() -> isize {
    let input = std::fs::read_to_string("resources/day23/day23.txt")
        .unwrap()
        .trim()
        .to_string();
    let op_map = parse_opcodes(input);

    let mut computers = (0..50)
        .map(|i| Computer {
            input: vec![i].into_iter().collect(),
            output: vec![],
            memory: op_map.clone(),
            cur_pos: 0,
            last_intruction: 0,
            relative_base_offset: 0,
        })
        .collect::<Vec<_>>();

    let mut nat = Nat::new();

    let mut idle_count = 0;
    loop {
        let mut pushed_comps: HashSet<usize> = HashSet::new();

        for i in 0..50 {
            let computer = &mut computers[i];

            computer.process_until_break_or_out();
            if computer.last_intruction == 99 {
                continue;
            }

            let maybe_address = computer.get_maybe_out();
            if maybe_address.is_none() {
                continue;
            }
            let address = maybe_address.unwrap();

            computer.process_until_break_or_out();
            let x = computer.get_last_out();
            computer.process_until_break_or_out();
            let y = computer.get_last_out();

            if address == 255 {
                nat.process_packet((x, y));
            } else if address < 50 && address >= 0 {
                pushed_comps.insert(address as usize);
                computers[address as usize].input.push_back(x);
                computers[address as usize].input.push_back(y);
            }
        }

        for i in 0..50 {
            if !pushed_comps.contains(&i) {
                computers[i].input.push_back(-1);
            }
        }

        let num_empty_queue = computers.iter().filter(|c| c.input.len() == 1).count();
        let no_out = pushed_comps.len() == 0;
        let is_idle = num_empty_queue == 50 && no_out;
        if is_idle {
            idle_count += 1;
            if idle_count >= 100 {
                idle_count = 0;
                nat.send_packet(&mut computers);
                if nat.is_duplicate() {
                    return nat.packet.1;
                }
            }
        }
    }
}
