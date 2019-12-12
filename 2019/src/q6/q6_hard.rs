use std::collections::HashMap;
use std::collections::HashSet;
use std::collections::VecDeque;
use std::io;
use std::io::BufRead;

fn build_jump_map(v: Vec<(String, String)>) -> HashMap<String, HashSet<String>> {
    let mut m: HashMap<String, HashSet<String>> = HashMap::with_capacity(v.len());

    for (key, val) in v {
        match m.get_mut(&key) {
            Some(h_set) => {
                h_set.insert(val.to_string());
            }
            None => {
                let mut new_set = HashSet::new();
                new_set.insert(val.to_string());
                m.insert(key.to_string(), new_set);
            }
        }

        match m.get_mut(&val) {
            Some(h_set) => {
                h_set.insert(key);
            }
            None => {
                let mut new_set = HashSet::new();
                new_set.insert(key);
                m.insert(val, new_set);
            }
        }
    }

    return m;
}

fn find_node_for(m: &HashMap<String, HashSet<String>>, entry: &str) -> String {
    for (key, h_set) in m {
        if h_set.contains(&entry.to_string()) {
            return key.to_string();
        }
    }

    panic!("No entry found for {}", entry);
}

fn find_min_path(m: &HashMap<String, HashSet<String>>, from: String, to: String) -> usize {
    let mut q = VecDeque::new();
    let mut visited = HashSet::new();
    q.push_back((&from, 0));

    while !q.is_empty() {
        let (next, dist) = q.pop_front().unwrap();
        visited.insert(next);
        if *next == to {
            return dist;
        }
        for neigh in m.get(next).unwrap() {
            if !visited.contains(neigh) {
                q.push_back((neigh, dist + 1));
            }
        }
    }

    panic!("Could not find path between {} and {}", from, to);
}

fn main() {
    let input = read_input_into_line_array();

    let parsed_input = parse(input);

    let jump_map = build_jump_map(parsed_input);

    let you_position = find_node_for(&jump_map, "YOU");
    let san_position = find_node_for(&jump_map, "SAN");

    let min_path = find_min_path(&jump_map, you_position, san_position);
    println!("{:?}", min_path);
}

fn parse(s: Vec<String>) -> Vec<(String, String)> {
    return s
        .iter()
        .map(|x| x.split(")").collect())
        .map(|x: Vec<&str>| (x[0].to_string(), x[1].to_string()))
        .collect();
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
