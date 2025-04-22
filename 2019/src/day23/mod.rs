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
            input: vec![i, -1].into_iter().collect(),
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
