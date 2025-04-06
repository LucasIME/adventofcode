#[cfg(test)]
mod tests {
    use aoc2019::day08;

    #[test]
    fn part1_works() {
        assert_eq!(2318, day08::part1());
    }

    #[test]
    fn part2_works() {
        let expected_vec = vec![
            " ▯▯  ▯  ▯ ▯▯▯▯  ▯▯  ▯▯▯  ",
            "▯  ▯ ▯  ▯ ▯    ▯  ▯ ▯  ▯ ",
            "▯  ▯ ▯▯▯▯ ▯▯▯  ▯    ▯▯▯  ",
            "▯▯▯▯ ▯  ▯ ▯    ▯    ▯  ▯ ",
            "▯  ▯ ▯  ▯ ▯    ▯  ▯ ▯  ▯ ",
            "▯  ▯ ▯  ▯ ▯     ▯▯  ▯▯▯  ",
            "",
        ];
        let expected_str = expected_vec.join("\n");

        assert_eq!(expected_str, day08::part2());
    }
}
