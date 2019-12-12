use std::io;
use std::io::BufRead;

fn get_digit_count(layer: &[u32], digit: u32) -> usize {
    return layer
        .iter()
        .filter(|x| **x == digit)
        .collect::<Vec<_>>()
        .len();
}

fn find_layer_with_fewest_0_digits(layers: Vec<&[u32]>) -> &[u32] {
    let mut min = std::usize::MAX;
    let mut chosen_layer: &[u32] = &[];
    for layer in &layers {
        let count = get_digit_count(&layer, 0);
        if count < min {
            min = count;
            chosen_layer = layer;
        }
    }

    return chosen_layer;
}

fn main() {
    let input = read_line();
    let parsed_input = parse(input);

    let w = 25;
    let h = 6;
    let chunk_size = w * h;

    let layers: Vec<&[u32]> = parsed_input.chunks(chunk_size).collect();

    let chosen_layer = find_layer_with_fewest_0_digits(layers);

    let resp = get_digit_count(&chosen_layer, 1) * get_digit_count(&chosen_layer, 2);

    println!("{:?}", resp);
}

fn parse(input: String) -> Vec<u32> {
    return input.chars().map(|c| c.to_digit(10).unwrap()).collect();
}

fn read_line() -> String {
    return io::stdin()
        .lock()
        .lines()
        .map(|res| res.ok())
        .filter(|x| x.is_some())
        .map(|x| x.unwrap())
        .next()
        .unwrap();
}
