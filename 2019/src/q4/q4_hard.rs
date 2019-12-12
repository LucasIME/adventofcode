use std::cmp;
use std::io;
use std::io::BufRead;

fn parse(input: String) -> (isize, isize) {
    let split: Vec<isize> = input
        .split("-")
        .map(|s| s.parse::<isize>().unwrap())
        .collect();

    return (split[0], split[1]);
}

fn is_valid_password(n: isize) -> bool {
    let n_str = n.to_string();
    return has_exact_two_similar_adjacent(&n_str) && doest_not_decrease(&n_str);
}

fn doest_not_decrease(s: &str) -> bool {
    let mut max = 0;
    for n in s.chars().map(|c| c.to_digit(10).unwrap()) {
        if n < max {
            return false;
        }
        max = cmp::max(max, n);
    }

    return true;
}

fn has_exact_two_similar_adjacent(s: &str) -> bool {
    let mut last_seen: char = ' ';
    let mut last_streak = 0;
    for c in s.chars() {
        if c == last_seen {
            last_streak += 1;
        } else {
            last_seen = c;
            if last_streak == 2 {
                return true;
            }
            last_streak = 1;
        }
    }

    return last_streak == 2;
}

fn main() {
    let raw_input = read_line();

    let (min, max) = parse(raw_input);

    let mut total = 0;
    for n in min..max + 1 {
        if is_valid_password(n) {
            total += 1;
        }
    }

    println!("{:?}", total);
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
