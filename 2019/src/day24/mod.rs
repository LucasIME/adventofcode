use std::collections::HashSet;

fn parse_to_matrix(lines: Vec<String>) -> Vec<Vec<char>> {
    let mut matrix = vec![];
    for line in lines {
        let chars = line.chars().collect::<Vec<_>>();
        matrix.push(chars);
    }
    return matrix;
}

fn is_valid_pos(matrix: &Vec<Vec<char>>, row: usize, col: usize) -> bool {
    return row < matrix.len() && col < matrix[row].len();
}

fn get_next_cell_state(matrix: Vec<Vec<char>>, row: usize, col: usize) -> char {
    let directions: Vec<(isize, isize)> = vec![
        (-1, 0), // up
        (1, 0),  // down
        (0, -1), // left
        (0, 1),  // right
    ];

    let mut neigh_count = 0;
    for dir_vec in directions {
        let (n_row, n_col) = (
            row as isize + dir_vec.0,
            col as isize + dir_vec.1,
        );

        if !is_valid_pos(&matrix, n_row as usize, n_col as usize) {
            continue;
        }

        if matrix[n_row as usize][n_col as usize] == '#' {
            neigh_count += 1;
        }
    }

    match matrix[row][col] {
        '#' => {
            if neigh_count == 1 {
                return '#';
            } 
            return '.';
        }
        '.' => {
            if neigh_count == 1 || neigh_count == 2 {
                return '#';
            } 
            return '.';
        }
        _ => panic!("Invalid cell state"),
    }
}

fn next_state(matrix: Vec<Vec<char>>) -> Vec<Vec<char>> {
    let mut next = matrix.clone();

    for row in 0..matrix.len() {
        for col in 0..matrix[0].len() {
            next[row][col] = get_next_cell_state(matrix.clone(), row, col);
        }
    }

    return next
}

fn get_biodiversity(matrix: Vec<Vec<char>>) -> isize {
    let mut bio = 0;
    for row in 0..matrix.len() {
        for col in 0..matrix[0].len() {
            if matrix[row][col] == '#' {
                bio +=  2_isize.pow((row * matrix[0].len() + col) as u32);
            }
        }
    }
    return bio;
}

pub fn part1(file_path: String) -> isize {
    let input = crate::utils::read_lines(file_path.as_str());
    let mut cur_matrix = parse_to_matrix(input);

    let mut seen_matrices: HashSet<Vec<Vec<char>>> = HashSet::new();
    seen_matrices.insert(cur_matrix.clone());

    loop {
        let next_matrix = next_state(cur_matrix);
        if seen_matrices.contains(&next_matrix) {
            return get_biodiversity(next_matrix);
        }

        seen_matrices.insert(next_matrix.clone());
        cur_matrix = next_matrix;
    }

}
