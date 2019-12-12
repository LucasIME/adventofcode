use std::io;
use std::io::BufRead;
use std::iter::FromIterator;

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

fn main() {
    let input = read_line();
    let parsed_input = parse(input);

    let w = 25;
    let h = 6;
    let chunk_size = w * h;

    let layers: Vec<&[u32]> = parsed_input.chunks(chunk_size).collect();

    let merged_layers = merge_layers(layers);

    let img_str = layer_to_img_str(merged_layers, w);

    println!("{}", img_str);
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
