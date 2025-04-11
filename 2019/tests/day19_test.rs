#[cfg(test)]
mod tests {
    use aoc2019::day19;

    #[test]
    fn part1_works() {
        assert_eq!(229, day19::part1());
    }

    #[test]
    fn part2_works() {
        assert_eq!(6950903, day19::part2());
    }
}
