use std::fs;

pub fn read_lines(file_path: &str) -> Vec<String> {
    let input = fs::read_to_string(file_path).unwrap();
    return input.lines().map(|line| line.to_string()).collect();
}
