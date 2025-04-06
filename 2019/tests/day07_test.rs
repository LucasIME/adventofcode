#[cfg(test)]
mod tests {
    use aoc2019::day07;

    #[test]
    fn part1_works() {
        assert_eq!(24625, day07::part1());
    }

    #[test]
    fn part2_works() {
        assert_eq!(36497698, day07::part2());
    }
}
