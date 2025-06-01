#[cfg(test)]
mod tests {
    use aoc2019::day22;
    use aoc2019::day22::Deck;

    #[test]
    fn example1_works() {
        assert_eq!(
            Deck {
                cards: vec![0, 3, 6, 9, 2, 5, 8, 1, 4, 7]
            },
            day22::apply_ops("resources/day22/test1.txt".to_string(), 10)
        );
    }

    #[test]
    fn example2_works() {
        assert_eq!(
            Deck {
                cards: vec![3, 0, 7, 4, 1, 8, 5, 2, 9, 6]
            },
            day22::apply_ops("resources/day22/test2.txt".to_string(), 10)
        );
    }

    #[test]
    fn example3_works() {
        assert_eq!(
            Deck {
                cards: vec![6, 3, 0, 7, 4, 1, 8, 5, 2, 9]
            },
            day22::apply_ops("resources/day22/test3.txt".to_string(), 10)
        );
    }

    #[test]
    fn example4_works() {
        assert_eq!(
            Deck {
                cards: vec![9, 2, 5, 8, 1, 4, 7, 0, 3, 6]
            },
            day22::apply_ops("resources/day22/test4.txt".to_string(), 10)
        );
    }

    #[test]
    fn part1_works() {
        assert_eq!(
            1,
            day22::part1("resources/day22/day22.txt".to_string(), 10007)
        );
    }
}
