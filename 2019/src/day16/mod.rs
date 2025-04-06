fn build_pattern(i: usize, size: usize) -> Vec<isize> {
    let zero_v = vec![0];
    let one_v = vec![1];
    let minus_one_v = vec![-1];

    let zero_it = zero_v.iter().cycle().take(i);
    let one_it = one_v.iter().cycle().take(i);
    let sec_zero_it = zero_v.iter().cycle().take(i);
    let minus_one_it = minus_one_v.iter().cycle().take(i);

    return zero_it
        .chain(one_it)
        .chain(sec_zero_it)
        .chain(minus_one_it)
        .map(|x| *x)
        .cycle()
        .skip(1)
        .take(size)
        .collect();
}

fn merge_fft(v: &Vec<isize>, pattern: Vec<isize>) -> isize {
    let sum: isize = v
        .iter()
        .enumerate()
        .map(|(i, x)| x * pattern[i])
        .sum::<isize>();

    let last_digit = sum
        .to_string()
        .chars()
        .last()
        .unwrap()
        .to_digit(10)
        .unwrap();

    return last_digit as isize;
}

fn fft(v: Vec<isize>) -> Vec<isize> {
    return v
        .iter()
        .enumerate()
        .map(|(i, _)| {
            let pattern = build_pattern(i + 1, v.len());
            return merge_fft(&v, pattern);
        })
        .collect();
}

fn parse(s: String) -> Vec<isize> {
    return s
        .chars()
        .map(|c| c.to_digit(10).unwrap() as isize)
        .collect();
}

pub fn part1() -> String {
    let input = std::fs::read_to_string("resources/day16/day16.txt")
        .unwrap()
        .trim()
        .to_string();
    let mut input_list = parse(input);

    for _ in 0..100 {
        input_list = fft(input_list);
    }

    let resp = input_list
        .iter()
        .take(8)
        .map(|x| (*x).to_string())
        .collect::<Vec<String>>()
        .join("");

    return resp;
}
