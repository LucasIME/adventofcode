use std::fs;

fn process_op_codes(opcodes: &mut Vec<usize>) -> &mut Vec<usize> {
    let mut cur_pos = 0;
    while cur_pos < opcodes.len() {
        match opcodes[cur_pos] {
            1 => {
                let sum =
                    opcodes[opcodes[cur_pos + 1] as usize] + opcodes[opcodes[cur_pos + 2] as usize];
                let pos_to_change = opcodes[cur_pos as usize + 3];
                opcodes[pos_to_change as usize] = sum;
            }
            2 => {
                let mul =
                    opcodes[opcodes[cur_pos + 1] as usize] * opcodes[opcodes[cur_pos + 2] as usize];
                let pos_to_change = opcodes[cur_pos + 3];
                opcodes[pos_to_change as usize] = mul;
            }
            99 => break,
            _ => println!("error!"),
        }
        cur_pos += 4;
    }
    return opcodes;
}

fn parse(input: String) -> Vec<usize> {
    return input
        .split(",")
        .map(|x| x.parse::<usize>().unwrap())
        .collect();
}

pub fn part1() -> usize {
    let raw_input = fs::read_to_string("resources/day02/day02.txt")
        .unwrap()
        .trim()
        .to_string();

    let mut op_array = parse(raw_input);

    op_array[1] = 12;
    op_array[2] = 2;

    let result = process_op_codes(&mut op_array);

    println!("{:?}", result[0]);
    return result[0];
}

fn find_input_pair_for_output(opcodes: &Vec<usize>, target: usize) -> Option<Vec<usize>> {
    for noun in 0..100 {
        for verb in 0..100 {
            let mut candidate_array = opcodes.clone();
            candidate_array[1] = noun;
            candidate_array[2] = verb;

            let result = process_op_codes(&mut candidate_array);

            if result[0] == target {
                return Some(result.to_vec());
            }
        }
    }

    println!("Didn't find array for target");
    return None;
}

pub fn part2() -> usize {
    let raw_input = fs::read_to_string("resources/day02/day02.txt")
        .unwrap()
        .trim()
        .to_string();

    let op_array = parse(raw_input);

    let result = find_input_pair_for_output(&op_array, 19690720).unwrap();

    return 100 * result[1] + result[2];
}
