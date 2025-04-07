use std::collections::HashMap;
use std::collections::VecDeque;
use crate::intcode::Computer;


fn parse(input: String) -> HashMap<usize, isize> {
    return input
        .split(",")
        .enumerate()
        .map(|(i, x)| (i, x.parse::<isize>().unwrap()))
        .collect();
}

fn remainder(a: isize, b: isize) -> isize {
    let base = a % b;
    if base < 0 {
        return base + b;
    }

    return base;
}

fn change_dir(cur_dir: isize, direction_to_turn: isize) -> isize {
    let mut new_dir_to_turn = direction_to_turn;
    if direction_to_turn == 0 {
        new_dir_to_turn = -1;
    }
    return remainder(cur_dir + new_dir_to_turn, 4);
}

fn change_pos(cur_pos: (isize, isize), direction: isize) -> (isize, isize) {
    let (x, y) = cur_pos;
    match direction {
        0 => (x, y + 1),
        1 => (x + 1, y),
        2 => (x, y - 1),
        3 => (x - 1, y),
        _ => panic!("Unknown direction to go!"),
    }
}

pub fn part1() -> usize {
    let input = std::fs::read_to_string("resources/day11/day11.txt")
        .unwrap()
        .trim()
        .to_string();
    let op_map = parse(input);

    let mut computer = Computer {
        input: (0..1).collect::<VecDeque<_>>(),
        output: vec![],
        memory: op_map,
        cur_pos: 0,
        last_intruction: 0,
        relative_base_offset: 0,
    };

    let mut cur_dir = 0; // 0 = up, 1 = right, 2 = down, 3 = left
    let mut cur_pos = (0, 0);
    let mut colored_set = HashMap::new();

    loop {
        computer.process_until_break_or_out();
        if computer.last_intruction == 99 {
            break;
        }

        let color_to_paint = computer.output.last().unwrap();
        colored_set.insert(cur_pos, *color_to_paint);

        computer.process_until_break_or_out();
        let dir_to_turn = computer.output.last().unwrap();
        cur_dir = change_dir(cur_dir, *dir_to_turn);
        cur_pos = change_pos(cur_pos, cur_dir);

        computer
            .input
            .push_back(*colored_set.get(&cur_pos).unwrap_or(&0));
    }

    let resp = colored_set.len();
    return resp;
}

fn to_output_matrix(colored_set: HashMap<(isize, isize), isize>) -> String {
    //Vec<Vec<char>> {
    let black = ' ';
    let white = 'W';
    let flip_y_map: HashMap<_, _> = colored_set
        .iter()
        .map(|((x, y), c)| ((*x, -*y), *c))
        .collect();

    let min_x = flip_y_map.iter().map(|((x, _y), _c)| *x).min().unwrap();
    let max_x = flip_y_map.iter().map(|((x, _y), _c)| *x).max().unwrap();
    let min_y = flip_y_map.iter().map(|((_x, y), _c)| *y).min().unwrap();
    let max_y = flip_y_map.iter().map(|((_x, y), _c)| *y).max().unwrap();

    let norm_max_x: usize = (max_x - min_x + 1) as usize;
    let norm_max_y: usize = (max_y - min_y + 1) as usize;

    let mut resp = vec![vec![black; norm_max_x]; norm_max_y];

    for ((x, y), c) in flip_y_map {
        if c == 1 {
            let norm_x = x - min_x;
            let norm_y = y - min_y;
            resp[norm_y as usize][norm_x as usize] = white;
        }
    }

    return resp
        .into_iter()
        .map(|v| v.into_iter().collect::<String>())
        .fold("".to_string(), |a, b| a + "\n" + &b);
}

pub fn part2() -> String {
    let input = std::fs::read_to_string("resources/day11/day11.txt")
        .unwrap()
        .trim()
        .to_string();
    let op_map = parse(input);

    let mut computer = Computer {
        input: (1..2).collect::<VecDeque<_>>(),
        output: vec![],
        memory: op_map,
        cur_pos: 0,
        last_intruction: 0,
        relative_base_offset: 0,
    };

    let mut cur_dir = 0; // 0 = up, 1 = right, 2 = down, 3 = left
    let mut cur_pos = (0, 0);
    let mut colored_set = HashMap::new();

    loop {
        computer.process_until_break_or_out();
        if computer.last_intruction == 99 {
            break;
        }

        let color_to_paint = computer.output.last().unwrap();
        colored_set.insert(cur_pos, *color_to_paint);

        computer.process_until_break_or_out();
        let dir_to_turn = computer.output.last().unwrap();
        cur_dir = change_dir(cur_dir, *dir_to_turn);
        cur_pos = change_pos(cur_pos, cur_dir);

        computer
            .input
            .push_back(*colored_set.get(&cur_pos).unwrap_or(&0));
    }

    let resp = to_output_matrix(colored_set);
    println!("{}", resp);

    return resp;
}
