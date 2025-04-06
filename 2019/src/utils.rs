use std::fs;

pub fn read_lines(file_path: &str) -> Vec<String> {
    let input = fs::read_to_string(file_path).unwrap();
    return input.lines().map(|line| line.to_string()).collect();
}

pub fn lcm(x: usize, y: usize) -> usize {
    let mut p = (x, y);

    while p.0 > 0 {
        p = (p.1 % p.0, p.0);
    }
    return x / p.1 * y;
}
