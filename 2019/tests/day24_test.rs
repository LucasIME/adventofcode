#[cfg(test)]
mod tests {
    use aoc2019::day24;

    #[test]
    fn part1_test1_works() {
        assert_eq!(2129920, day24::part1("resources/day24/test1.txt".to_string()));
    }

    #[test]
    fn part1_works() {
        assert_eq!(23846449, day24::part1("resources/day24/day24.txt".to_string()));
    }

    #[test]
    fn part2_test_works() {
        assert_eq!(99, day24::part2("resources/day24/test1.txt".to_string(), 10));
    }

    #[test]
    fn part2_works() {
        assert_eq!(1934, day24::part2("resources/day24/day24.txt".to_string(), 200));
    }
}
