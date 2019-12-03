use std::collections::HashMap;
use std::collections::HashSet;
use std::io;
use std::io::BufRead;

#[derive(Debug)]
struct Command {
    direction: char,
    steps: isize,
}

fn substr(s: &str, start_pos: usize) -> String {
    return s.chars().skip(start_pos).collect();
}

fn get_commands_from_str(s: &str) -> Vec<Command> {
    return s
        .split(",")
        .map(|s| {
            let dir = s.chars().nth(0).unwrap();
            let steps = substr(s, 1).parse::<isize>().unwrap();

            Command {
                direction: dir,
                steps: steps,
            }
        })
        .collect();
}

fn parse(input_array: Vec<String>) -> (Vec<Command>, Vec<Command>) {
    let first_path = get_commands_from_str(&input_array[0]);
    let second_path = get_commands_from_str(&input_array[1]);

    return (first_path, second_path);
}

fn get_visited_points_from_path(path: &Vec<Command>) -> HashMap<(isize, isize), isize> {
    let mut current_position = (0, 0);
    let mut visited = HashMap::new();
    let mut total_steps = 0;

    for command in path {
        match command.direction {
            'U' => {
                for step in 1..command.steps + 1 {
                    let new_pos = (current_position.0, current_position.1 + step);
                    if !visited.contains_key(&new_pos) {
                        visited.insert(new_pos, total_steps + step);
                    }
                }
                current_position.1 += command.steps;
            }
            'R' => {
                for step in 1..command.steps + 1 {
                    let new_pos = (current_position.0 + step, current_position.1);
                    if !visited.contains_key(&new_pos) {
                        visited.insert(new_pos, total_steps + step);
                    }
                }
                current_position.0 += command.steps;
            }
            'D' => {
                for step in 1..command.steps + 1 {
                    let new_pos = (current_position.0, current_position.1 - step);
                    if !visited.contains_key(&new_pos) {
                        visited.insert(new_pos, total_steps + step);
                    }
                }
                current_position.1 -= command.steps;
            }
            'L' => {
                for step in 1..command.steps + 1 {
                    let new_pos = (current_position.0 - step, current_position.1);
                    if !visited.contains_key(&new_pos) {
                        visited.insert(new_pos, total_steps + step);
                    }
                }
                current_position.0 -= command.steps;
            }
            _ => println!("unknown command!"),
        }
        total_steps += command.steps;
    }

    return visited;
}

fn find_closest_intersection_distance(
    first_path: &Vec<Command>,
    second_path: &Vec<Command>,
) -> isize {
    let starting_position = (0, 0);

    let visited_with_dist_a = get_visited_points_from_path(first_path);
    let visited_with_dist_b = get_visited_points_from_path(second_path);

    let visited_a: HashSet<(isize, isize)> = visited_with_dist_a.keys().map(|x| *x).collect();
    let visited_b: HashSet<(isize, isize)> = visited_with_dist_b.keys().map(|x| *x).collect();

    let mut points_in_common: HashSet<&(isize, isize)> =
        visited_a.intersection(&visited_b).collect();
    points_in_common.remove(&starting_position);

    let mut min_dist = isize::max_value();
    for point in points_in_common {
        let a_dist = visited_with_dist_a.get(point).unwrap();
        let b_dist = visited_with_dist_b.get(point).unwrap();
        let dist = a_dist + b_dist;

        if dist < min_dist {
            min_dist = dist;
        }
    }

    return min_dist;
}

fn main() {
    let input = read_input_into_line_array();

    let (first_path, second_path) = parse(input);

    let dist = find_closest_intersection_distance(&first_path, &second_path);

    println!("{:?}", dist);
}

fn read_input_into_line_array() -> Vec<String> {
    return io::stdin()
        .lock()
        .lines()
        .map(|res| res.ok())
        .filter(|x| x.is_some())
        .map(|x| x.unwrap())
        .collect();
}
