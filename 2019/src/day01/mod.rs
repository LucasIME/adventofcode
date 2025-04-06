use std::fs;

fn get_fuel_array(input: Vec<i32>) -> Vec<i32> {
    return input.iter().map(|x| x / 3 - 2).collect();
}

fn get_fuel_array2(input: Vec<i32>) -> Vec<i32> {
    return input.iter().map(get_fuel_for_mass).collect();
}

fn get_fuel_for_mass(mass: &i32) -> i32 {
    let mut resp = 0;
    let mut current_mass = *mass;
    while current_mass > 0 {
        let fuel_needed = current_mass / 3 - 2;
        if fuel_needed > 0 {
            resp += fuel_needed;
        }
        current_mass = fuel_needed;
    }
    return resp;
}

pub fn part1() -> i32 {
    let raw_input = fs::read_to_string("resources/day01/day01.txt").unwrap();
    let input = parse_input(raw_input);
    let fuel_array = get_fuel_array(input);

    let resp: i32 = fuel_array.iter().sum();

    println!("{:?}", resp);
    return resp;
}

pub fn part2() -> i32 {
    let raw_input = fs::read_to_string("resources/day01/day01.txt").unwrap();
    let input = parse_input(raw_input);
    let fuel_array = get_fuel_array2(input);

    let resp: i32 = fuel_array.iter().sum();

    return resp;
}

fn parse_input(input: String) -> Vec<i32> {
    return input.lines().map(|s| s.parse::<i32>().unwrap()).collect();
}
