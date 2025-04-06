use std::collections::HashMap;
use std::collections::HashSet;
use std::collections::VecDeque;

fn build_map(v: Vec<(String, String)>) -> HashMap<String, HashSet<String>> {
    let mut m: HashMap<String, HashSet<String>> = HashMap::with_capacity(v.len());

    for (key, val) in v {
        if m.contains_key(&key) {
            m.get_mut(&key).unwrap().insert(val);
        } else {
            let mut new_set = HashSet::new();
            new_set.insert(val);
            m.insert(key, new_set);
        }
    }

    return m;
}

fn get_indirect(m: HashMap<String, HashSet<String>>) -> usize {
    let mut size = 0;
    for v_vec in m.values() {
        for val in v_vec {
            size += get_chain_size_from(&m, val);
        }
    }

    return size;
}

fn get_direct(m: &HashMap<String, HashSet<String>>) -> usize {
    let mut total = 0;
    for val in m.values() {
        total += val.len();
    }
    return total;
}

fn get_chain_size_from(m: &HashMap<String, HashSet<String>>, key: &String) -> usize {
    let mut size = 0;
    if !m.contains_key(key) {
        return 0;
    }

    for val in m.get(key).unwrap() {
        size += 1 + get_chain_size_from(&m, val);
    }

    return size;
}

fn parse(s: Vec<String>) -> Vec<(String, String)> {
    return s
        .iter()
        .map(|x| x.split(")").collect())
        .map(|x: Vec<&str>| (x[0].to_string(), x[1].to_string()))
        .collect();
}

pub fn part1() -> usize {
    let input = crate::utils::read_lines("resources/day06/day06.txt");

    let parsed_input = parse(input);

    let relation_map = build_map(parsed_input);

    let direct = get_direct(&relation_map);
    let indirect = get_indirect(relation_map);

    return direct + indirect;
}

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

pub fn part2() -> usize {
    let input = crate::utils::read_lines("resources/day06/day06.txt");

    let parsed_input = parse(input);

    let jump_map = build_jump_map(parsed_input);

    let you_position = find_node_for(&jump_map, "YOU");
    let san_position = find_node_for(&jump_map, "SAN");

    let min_path = find_min_path(&jump_map, you_position, san_position);
    return min_path;
}
