enum Operations {
    Deal,
    DealWithIncrement(i32),
    Cut(i32),
}

#[derive(Debug, PartialEq)]
pub struct Deck {
    pub cards: Vec<i32>,
}

impl Deck {
    fn new(size: i32) -> Deck {
        Deck {
            cards: (0..size).collect(),
        }
    }

    fn apply_operation(&mut self, operation: Operations) -> Deck {
        return match operation {
            Operations::Deal => Deck {
                cards: self.cards.iter().cloned().rev().collect(),
            },
            Operations::DealWithIncrement(increment) => {
                let mut new_deck = vec![0; self.cards.len()];

                for i in 0..self.cards.len() {
                    let new_position = (i * increment as usize) % self.cards.len();
                    new_deck[new_position] = self.cards[i];
                }

                Deck { cards: new_deck }
            }
            Operations::Cut(cut) => {
                let cut = if cut < 0 {
                    self.cards.len() as i32 + cut
                } else {
                    cut
                };

                let first_batch = self
                    .cards
                    .iter()
                    .take(cut as usize)
                    .cloned()
                    .collect::<Vec<i32>>();
                let remaining = self
                    .cards
                    .iter()
                    .skip(cut as usize)
                    .cloned()
                    .collect::<Vec<i32>>();

                Deck {
                    cards: remaining.into_iter().chain(first_batch).collect(),
                }
            }
        };
    }
}

fn parse_line(line: &str) -> Operations {
    if line.starts_with("deal into new stack") {
        return Operations::Deal;
    }

    if line.starts_with("deal with increment") {
        let increment: i32 = line.split_whitespace().last().unwrap().parse().unwrap();
        return Operations::DealWithIncrement(increment);
    }

    if line.starts_with("cut") {
        let cut: i32 = line.split_whitespace().last().unwrap().parse().unwrap();
        return Operations::Cut(cut);
    }

    panic!("Unknown operation: {}", line);
}

pub fn apply_ops(file_path: String, deck_size: i32) -> Deck {
    let input = crate::utils::read_lines(file_path.as_str());
    let mut deck = Deck::new(deck_size);
    let ops = input
        .iter()
        .map(|line| parse_line(line))
        .collect::<Vec<Operations>>();

    for operation in ops {
        deck = deck.apply_operation(operation);
    }

    return deck;
}

pub fn part1(file_path: String, deck_size: i32) -> usize {
    let deck = apply_ops(file_path, deck_size);
    return deck.cards.iter().position(|&x| x == 2019).unwrap();
}
