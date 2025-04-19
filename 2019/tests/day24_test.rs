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
}
