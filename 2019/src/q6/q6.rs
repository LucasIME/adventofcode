use std::collections::HashMap;
use std::collections::HashSet;
use std::io;
use std::io::BufRead;

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

fn main() {
    let input = read_input_into_line_array();

    let parsed_input = parse(input);

    let relation_map = build_map(parsed_input);

    let direct = get_direct(&relation_map);
    let indirect = get_indirect(relation_map);

    println!("{:?}", direct + indirect);
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
