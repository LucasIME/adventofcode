#[cfg(test)]
mod tests {
    use aoc2019::day11;

    #[test]
    fn part1_works() {
        assert_eq!(2268, day11::part1());
    }

    #[test]
    fn part2_works() {
        let expected_vec: Vec<String> = vec![
            " WW  WWWW WWW  W  W WWWW   WW  WW  WWW  ",
            "W  W W    W  W W W     W    W W  W W  W",
            "W    WWW  W  W WW     W     W W    W  W",
            "W    W    WWW  W W   W      W W    WWW ",
            "W  W W    W    W W  W    W  W W  W W W ",
            " WW  WWWW W    W  W WWWW  WW   WW  W  W",
        ]
        .iter()
        .map(|x| x.trim().to_string())
        .collect();

        let split_out: Vec<String> = day11::part2()
            .split("\n")
            .map(|x| x.trim().to_string())
            .filter(|x| *x != "")
            .collect();

        assert_eq!(expected_vec, split_out);
    }
}
