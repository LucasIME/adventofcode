use std::io;
use std::io::BufRead;

fn get_fuel_array(input: Vec<i32>) -> Vec<i32> {
    return input
        .iter()
        .map(|x|x/3 - 2 )
        .collect();
}

fn main() {
    let input = read_input_into_line_array();
    let fuel_array = get_fuel_array(input);

    let resp: i32 = fuel_array
        .iter()
        .sum();

    println!("{:?}" ,resp);
}

fn read_input_into_line_array() -> Vec<i32> {
    return io::stdin()
        .lock()
        .lines()
        .map(|res|res.ok())
        .filter(|x|x.is_some())
        .map(|x|x.unwrap())
        .map(|s|s.parse::<i32>().unwrap())
        .collect();
}
