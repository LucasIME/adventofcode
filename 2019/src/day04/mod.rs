use std::cmp;
use std::fs;

fn parse(input: String) -> (isize, isize) {
    let split: Vec<isize> = input
        .split("-")
        .map(|s| s.parse::<isize>().unwrap())
        .collect();

    return (split[0], split[1]);
}

fn is_valid_password(n: isize) -> bool {
    let n_str = n.to_string();
    return two_adjacent_are_the_same(&n_str) && doest_not_decrease(&n_str);
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

fn two_adjacent_are_the_same(s: &str) -> bool {
    let mut last_seen: char = ' ';
    for c in s.chars() {
        if c == last_seen {
            return true;
        }
        last_seen = c;
    }
    return false;
}

pub fn part1() -> i32 {
    let raw_input = fs::read_to_string("resources/day04/day04.txt")
        .unwrap()
        .trim()
        .to_string();

    let (min, max) = parse(raw_input);

    let mut total = 0;
    for n in min..max + 1 {
        if is_valid_password(n) {
            total += 1;
        }
    }

    return total;
}

fn is_valid_password2(n: isize) -> bool {
    let n_str = n.to_string();
    return has_exact_two_similar_adjacent(&n_str) && doest_not_decrease(&n_str);
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

pub fn part2() -> i32 {
    let raw_input = fs::read_to_string("resources/day04/day04.txt")
        .unwrap()
        .trim()
        .to_string();

    let (min, max) = parse(raw_input);

    let mut total = 0;
    for n in min..max + 1 {
        if is_valid_password2(n) {
            total += 1;
        }
    }

    return total;
}
