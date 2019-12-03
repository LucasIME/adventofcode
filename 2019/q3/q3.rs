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

fn get_visited_points_from_path(path: &Vec<Command>) -> HashSet<(isize, isize)> {
    let mut current_position = (0, 0);
    let mut visited = HashSet::new();

    for command in path {
        match command.direction {
            'U' => {
                let old_y = current_position.1;
                let new_y = old_y + command.steps;
                current_position.1 = new_y;

                for y in old_y..new_y + 1 {
                    visited.insert((current_position.0, y));
                }
            }
            'R' => {
                let old_x = current_position.0;
                let new_x = old_x + command.steps;
                current_position.0 = new_x;

                for x in old_x..new_x + 1 {
                    visited.insert((x, current_position.1));
                }
            }
            'D' => {
                let old_y = current_position.1;
                let new_y = old_y - command.steps;
                current_position.1 = new_y;

                for y in new_y..old_y + 1 {
                    visited.insert((current_position.0, y));
                }
            }
            'L' => {
                let old_x = current_position.0;
                let new_x = old_x - command.steps;
                current_position.0 = new_x;

                for x in new_x..old_x + 1 {
                    visited.insert((x, current_position.1));
                }
            }
            _ => println!("unknown command!"),
        }
    }

    return visited;
}

fn find_closest_intersection(
    first_path: &Vec<Command>,
    second_path: &Vec<Command>,
) -> (isize, isize) {
    let starting_position = (0, 0);

    let visited_a = get_visited_points_from_path(first_path);
    let visited_b = get_visited_points_from_path(second_path);

    let mut points_in_common: HashSet<&(isize, isize)> =
        visited_a.intersection(&visited_b).collect();
    points_in_common.remove(&starting_position);

    let mut min_dist = isize::max_value();
    let mut closest_point = (0, 0);
    for point in points_in_common {
        let dist = point.0.abs() + point.1.abs();

        if dist < min_dist {
            closest_point = *point;
            min_dist = dist;
        }
    }

    return closest_point;
}

fn main() {
    let input = read_input_into_line_array();

    let (first_path, second_path) = parse(input);

    let closest_intersection = find_closest_intersection(&first_path, &second_path);

    let dist = closest_intersection.0.abs() + closest_intersection.1.abs();

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
