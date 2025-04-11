use crate::intcode::Computer;
use crate::intcode::parse_opcodes;
use std::collections::VecDeque;

fn output_to_grid(out: Vec<isize>) -> Vec<Vec<char>> {
    let whitelisted_chars = [10, 35, 46, 94, 60, 62, 86];
    return out
        .iter()
        .filter(|x| whitelisted_chars.contains(x))
        .map(|x| (*x as u8 as char).to_string())
        .collect::<Vec<String>>()
        .join("")
        .split("\n")
        .map(|x| x.to_string())
        .map(|s| s.chars().collect::<Vec<char>>())
        .filter(|v| !v.is_empty())
        .collect();
}

fn get(grid: &Vec<Vec<char>>, row: isize, col: isize) -> Option<char> {
    let row_n = grid.len() as isize;
    let col_n = grid[0].len() as isize;
    if row < 0 || row >= row_n || col < 0 || col >= col_n {
        return None;
    }

    return Some(grid[row as usize][col as usize]);
}

fn all_neigh_hashtag(grid: &Vec<Vec<char>>, row: isize, col: isize) -> bool {
    let coords = vec![
        (row, col),
        (row - 1, col),
        (row + 1, col),
        (row, col + 1),
        (row, col - 1),
    ];
    let hashtags = coords
        .iter()
        .map(|&(r, c)| get(grid, r, c))
        .filter(|c| c.is_some())
        .map(|opt| opt.unwrap())
        .filter(|c| *c == '#')
        .count();

    return hashtags == coords.len();
}

fn get_align_sum(grid: Vec<Vec<char>>) -> usize {
    let mut sum = 0;
    for row in 0..grid.len() {
        for col in 0..grid[row].len() {
            if all_neigh_hashtag(&grid, row as isize, col as isize) {
                sum += row * col;
            }
        }
    }

    return sum;
}

pub fn part1() -> usize {
    let input = std::fs::read_to_string("resources/day17/day17.txt")
        .unwrap()
        .trim()
        .to_string();
    let mut op_map = parse_opcodes(input);
    op_map.insert(0, 2);

    let mut computer = Computer {
        input: VecDeque::new(),
        output: vec![],
        memory: op_map,
        cur_pos: 0,
        last_intruction: 0,
        relative_base_offset: 0,
    };

    computer.process_until_break();
    let grid = output_to_grid(computer.output);
    let resp = get_align_sum(grid);
    return resp;
}
