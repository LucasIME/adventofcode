#[cfg(test)]
mod tests {
    use aoc2019::day09;

    #[test]
    fn part1_works() {
        assert_eq!(2351176124, day09::part1());
    }

    #[test]
    fn part2_works() {
        assert_eq!(73110, day09::part2());
    }
}
