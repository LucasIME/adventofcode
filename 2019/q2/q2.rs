use std::io;
use std::io::BufRead;

fn process_op_codes(opcodes: &mut Vec<i32>) -> &mut Vec<i32> {
    let mut cur_pos = 0;
    while cur_pos < opcodes.len() {
        match opcodes[cur_pos] {
            1 => {
                let sum = opcodes[opcodes[cur_pos+1] as usize] + opcodes[opcodes[cur_pos+2] as usize];
                let pos_to_change = opcodes[cur_pos as usize + 3];
                opcodes[pos_to_change as usize] = sum;
            },
            2 => {
                let mul = opcodes[opcodes[cur_pos+1] as usize] * opcodes[opcodes[cur_pos+2] as usize];
                let pos_to_change = opcodes[cur_pos + 3];
                opcodes[pos_to_change as usize] = mul;

            },
            99 => break,
            _ => println!("error!")
        }
        cur_pos += 4;
    }
    return opcodes
}

fn parse(input: String) -> Vec<i32> {
    return input.split(",")
        .map(|x|x.parse::<i32>().unwrap())
        .collect();
}

fn main() {
    let input = read_line();
    let mut op_array = parse(input);

    op_array[1] = 12;
    op_array[2] = 2;

    let result = process_op_codes(&mut op_array);

    println!("{:?}", result[0]);
}

fn read_line() -> String {
    return io::stdin()
        .lock()
        .lines()
        .map(|res|res.ok())
        .filter(|x|x.is_some())
        .map(|x|x.unwrap())
        .next()
        .unwrap();
}
