use std::collections::HashSet;
use std::collections::HashMap;

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

#[derive(Debug, Clone)]
struct Grid {
    grid: Vec<Vec<char>>,
    level: i32,
} 

impl Grid {
    fn new_empty(level: i32) -> Self {
        let grid = vec![vec!['.'; 5]; 5];
        return Grid { grid, level }
    }
}

fn next_grid_state(universe: &HashMap<i32, Grid>, grid: &Grid) -> Grid {
    let mut next = grid.clone();

    let matrix = &grid.grid;
    for row in 0..matrix.len() {
        for col in 0..matrix[0].len() {
            if row == 2 && col == 2 {
                next.grid[row][col] = '.';
                continue;
            }
            next.grid[row][col] = get_next_grid_cell_state(universe, grid, row, col);
        }
    }

    return next
}

enum Direction {
    Up,
    Down,
    Left,
    Right,
}

impl Direction {
    fn get_vec(&self) -> (isize, isize) {
        match self {
            Direction::Up => (-1, 0),
            Direction::Down => (1, 0),
            Direction::Left => (0, -1),
            Direction::Right => (0, 1),
        }
    }

    fn opposite(&self) -> Direction {
        match self {
            Direction::Up => Direction::Down,
            Direction::Down => Direction::Up,
            Direction::Left => Direction::Right,
            Direction::Right => Direction::Left,
        }
    }
}

#[derive(Debug, Clone)]
struct Coordinates {
    level: i32,
    row: usize,
    col: usize,
}

fn get_side_from(level: i32, dir: Direction) -> Vec<Coordinates> {
    let (start, vec) = match dir {
        Direction::Up => ((0, 0), (0, 1)),
        Direction::Down => ((4, 0), (0, 1)),
        Direction::Left => ((0, 0), (1, 0)),
        Direction::Right => ((0, 4), (1, 0)),
    };

    let mut neighs = vec![];
    for i in 0..5 {
        let (row, col) = (
            start.0 + vec.0 * i,
            start.1 + vec.1 * i,
        );

        neighs.push(Coordinates { level, row, col });
    }

    return neighs;
}

fn get_uni_cell(universe: &HashMap<i32, Grid>, level: i32, row: usize, col: usize) -> char {
    return universe.get(&level).map(|grid| grid.grid[row][col]).unwrap_or('.');
}

fn get_neighs(grid: &Grid, row: usize, col: usize) -> Vec<Coordinates> {
    let directions = vec![Direction::Up, Direction::Down, Direction::Left, Direction::Right];
    let mut neighs = vec![];
    for dir in directions {
        let dir_vec = dir.get_vec();
        let (n_row, n_col) = (
            row as isize + dir_vec.0,
            col as isize + dir_vec.1,
        );

        // Going to next depth
        if n_row == 2 && n_col == 2 {
            let some_neighs = get_side_from(grid.level + 1, dir.opposite());
            neighs.extend(some_neighs);
            continue;
        }

        // Going to previous depth
        if n_row < 0  {
            neighs.push(Coordinates { level: grid.level - 1, row: 1, col: 2 });
            continue;
        } else if n_row > 4 {
            neighs.push(Coordinates { level: grid.level - 1, row: 3, col: 2 });
            continue;
        } else if n_col < 0 {
            neighs.push(Coordinates { level: grid.level - 1, row: 2, col: 1 });
            continue;
        } else if n_col > 4 {
            neighs.push(Coordinates { level: grid.level - 1, row: 2, col: 3 });
            continue;
        }

        // Cur Depth neigh
        neighs.push(Coordinates { level: grid.level, row: n_row as usize, col: n_col as usize });
    }

    return neighs;
}

fn get_next_grid_cell_state(universe: &HashMap<i32, Grid>, grid: &Grid, row: usize, col: usize) -> char {
    let neighs = get_neighs(grid, row, col);
    let neigh_count = neighs.iter().filter(|&c| get_uni_cell(universe, c.level, c.row, c.col) == '#').count();
    let matrix = &grid.grid;

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

fn count_bugs(universe: &HashMap<i32, Grid>) -> isize {
    let mut count = 0;
    for grid in universe.values() {
        for row in 0..grid.grid.len() {
            for col in 0..grid.grid[0].len() {
                if grid.grid[row][col] == '#' {
                    count += 1;
                }
            }
        }
    }
    return count;
}

fn update_universe(universe: &mut HashMap<i32, Grid>) -> HashMap<i32, Grid> {
    let mut new_universe = universe.clone();

    for (level, grid) in universe.iter() {
        let next_grid = next_grid_state(universe, grid);
        new_universe.insert(*level, next_grid);
    }

    let min_index = *universe.keys().min().unwrap();
    let max_index = *universe.keys().max().unwrap();

    let new_low = min_index - 1;
    let new_high = max_index + 1;
    new_universe.insert(new_low, Grid::new_empty(new_low));
    new_universe.insert(new_high, Grid::new_empty(new_high));

    return new_universe;
}

pub fn part2(file_path: String, iterations: i32) -> isize {
    let input = crate::utils::read_lines(file_path.as_str());
    let cur_matrix = parse_to_matrix(input);

    let mut universe: HashMap<i32, Grid> = HashMap::new();
    universe.insert(0, Grid { grid: cur_matrix, level: 0 });
    universe.insert(1, Grid::new_empty(1));
    universe.insert(-1, Grid::new_empty(-1));

    for _ in 0..iterations {
        universe = update_universe(&mut universe);
    }

    return count_bugs(&universe);
}
