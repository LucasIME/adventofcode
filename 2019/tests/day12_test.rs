#[cfg(test)]
mod tests {
    use aoc2019::day12;

    #[test]
    fn part1_works() {
        assert_eq!(7687, day12::part1());
    }

    #[test]
    fn part2_works() {
        assert_eq!(334945516288044, day12::part2());
    }
}
