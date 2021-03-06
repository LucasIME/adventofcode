use std::io;
use std::io::BufRead;

fn process_op_codes(opcodes: &mut Vec<usize>) -> &mut Vec<usize> {
    let mut cur_pos = 0;
    while cur_pos < opcodes.len() {
        match opcodes[cur_pos] {
            1 => {
                let sum = opcodes[opcodes[cur_pos + 1]] + opcodes[opcodes[cur_pos + 2]];
                let pos_to_change = opcodes[cur_pos + 3];
                opcodes[pos_to_change] = sum;
            }
            2 => {
                let mul = opcodes[opcodes[cur_pos + 1]] * opcodes[opcodes[cur_pos + 2]];
                let pos_to_change = opcodes[cur_pos + 3];
                opcodes[pos_to_change] = mul;
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

fn main() {
    let input = read_line();
    let op_array = parse(input);

    let result = find_input_pair_for_output(&op_array, 19690720).unwrap();

    println!("{:?}", 100 * result[1] + result[2]);
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
