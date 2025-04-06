#[cfg(test)]
mod tests {
    use aoc2019::day02;

    #[test]
    fn part1_works() {
        assert_eq!(3716250, day02::part1());
    }

    #[test]
    fn part2_works() {
        assert_eq!(6472, day02::part2());
    }
}
