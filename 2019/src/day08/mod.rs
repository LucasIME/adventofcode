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

pub fn part1() -> usize {
    let input = std::fs::read_to_string("resources/day08/day08.txt")
        .unwrap()
        .trim()
        .to_string();
    let parsed_input = parse(input);

    let w = 25;
    let h = 6;
    let chunk_size = w * h;

    let layers: Vec<&[u32]> = parsed_input.chunks(chunk_size).collect();

    let chosen_layer = find_layer_with_fewest_0_digits(layers);

    let resp = get_digit_count(&chosen_layer, 1) * get_digit_count(&chosen_layer, 2);

    return resp;
}

fn merge_layers(layers: Vec<&[u32]>) -> Vec<u32> {
    let mut accumulated_layer: Vec<u32> = (0..layers[0].len()).map(|_| 2).collect();

    layers.iter().for_each(|layer| {
        layer.iter().enumerate().for_each(|(i, v)| {
            if accumulated_layer[i] == 2 {
                accumulated_layer[i] = *v;
            }
        })
    });

    return accumulated_layer;
}

fn layer_to_img_str(layer: Vec<u32>, width: usize) -> String {
    let str_array: Vec<String> = layer
        .iter()
        .map(|digit| {
            match digit {
                0 => " ",        // Mapping black to blank space to make reading easier.
                1 => "\u{25af}", // Mapping white to rectangle to make reading easier.
                2 => " ",
                _ => panic!("Unknown color when processing layer"),
            }
        })
        .map(|s| s.to_string())
        .collect();
    return String::from_iter(
        str_array
            .chunks(width)
            .map(|v| String::from_iter(v.iter().map(|s| s.to_string())) + "\n"),
    );
}

pub fn part2() -> String {
    let input = std::fs::read_to_string("resources/day08/day08.txt")
        .unwrap()
        .trim()
        .to_string();
    let parsed_input = parse(input);

    let w = 25;
    let h = 6;
    let chunk_size = w * h;

    let layers: Vec<&[u32]> = parsed_input.chunks(chunk_size).collect();

    let merged_layers = merge_layers(layers);

    let img_str = layer_to_img_str(merged_layers, w);
    println!("{}", img_str);
    return img_str;
}

fn parse(input: String) -> Vec<u32> {
    return input.chars().map(|c| c.to_digit(10).unwrap()).collect();
}
