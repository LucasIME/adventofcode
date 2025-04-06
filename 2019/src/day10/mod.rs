use std::collections::HashSet;

use crate::utils;

fn get_ast_pos(map: &Vec<String>) -> Vec<(usize, usize)> {
    return map
        .iter()
        .enumerate()
        .flat_map(|(row, s)| s.char_indices().map(move |(col, c)| (row, col, c)))
        .filter(|(_, _, c)| *c == '#')
        .map(|(row, col, _)| (row, col))
        .collect();
}

fn truncate(s: &str, max_chars: usize) -> &str {
    match s.char_indices().nth(max_chars) {
        None => s,
        Some((idx, _)) => &s[..idx],
    }
}

// Hack to make the float hashable and be able to store in the visited set, but not with too much
// precision so angle colide
fn get_normalize_angle(w: isize, h: isize) -> (String, String) {
    let norm = ((h * h + w * w) as f64).sqrt();
    return (
        truncate(&(w as f64 / norm).to_string(), 10).to_string(),
        truncate(&(h as f64 / norm).to_string(), 10).to_string(),
    );
}

fn get_dist_from_single_ast(row: usize, col: usize, ast_pos: &Vec<(usize, usize)>) -> usize {
    let mut visited_angles = HashSet::new();

    for (other_row, other_col) in ast_pos {
        if *other_row == row && *other_col == col {
            continue;
        }

        let angle = get_normalize_angle(
            *other_col as isize - col as isize,
            *other_row as isize - row as isize,
        );
        visited_angles.insert(angle);
    }

    return visited_angles.len();
}

fn get_max_reachable(ast_pos: Vec<(usize, usize)>) -> usize {
    let mut max = 0;

    for (row, col) in ast_pos.iter() {
        let dist = get_dist_from_single_ast(*row, *col, &ast_pos);
        if dist > max {
            max = dist;
        }
    }

    return max;
}

pub fn part1() -> usize {
    let input = utils::read_lines("resources/day10/day10.txt");
    let ast_pos = get_ast_pos(&input);

    return get_max_reachable(ast_pos);
}
