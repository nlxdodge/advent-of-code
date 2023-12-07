use std::{cmp::Ordering, collections::HashMap};

mod utils;

fn main() {
    let day = 7;
    let filepath = format!("src/inputs/aoc{:0>2}.txt", day);
    let contents = &utils::read_file(filepath.to_string());
    let lines: Vec<&str> = contents.lines().collect();

    let mut hands: Vec<Hand> = lines
        .iter()
        .map(|l| Hand {
            cards: l.split(' ').next().unwrap().to_owned(),
            bid: l.split(' ').nth(1).unwrap().parse::<i32>().unwrap(),
            rank: 0,
        })
        .collect();
    let mut jokers_hands = hands.clone();

    let star1: i64 = map_to_ranks(
        hands
            .iter_mut()
            .map(|jh| jh.calculate_score(false))
            .collect::<Vec<_>>(),
        false,
    )
    .iter()
    .map(|rh| (rh.bid * rh.rank) as i64)
    .sum();

    let stuff = jokers_hands
        .iter_mut()
        .map(|jh| jh.calculate_score(true))
        .collect::<Vec<_>>();

    let star2: i64 = map_to_ranks(
        jokers_hands
            .iter_mut()
            .map(|jh| jh.calculate_score(true))
            .collect::<Vec<_>>(),
        true,
    )
    .iter()
    .map(|rh| (rh.bid * rh.rank) as i64)
    .sum();

    println!("Day {day} ⭐1️⃣  result: {star1}");
    println!("Day {day} ⭐2️⃣  result: {star2}");
}

fn map_to_ranks(hands: Vec<Hand>, joker: bool) -> Vec<Hand> {
    let mut ranked_hands = hands;
    ranked_hands.sort_by(|a, b| match a.rank.cmp(&b.rank) {
        Ordering::Greater => Ordering::Greater,
        Ordering::Less => Ordering::Less,
        Ordering::Equal => {
            for index in 0..=4 {
                return match card_strength(a.cards.chars().nth(index).unwrap(), joker)
                    .cmp(&card_strength(b.cards.chars().nth(index).unwrap(), joker))
                {
                    Ordering::Greater => Ordering::Greater,
                    Ordering::Less => Ordering::Less,
                    Ordering::Equal => continue,
                };
            }
            Ordering::Equal
        }
    });
    ranked_hands
        .iter_mut()
        .enumerate()
        .for_each(|c| c.1.rank = (c.0 + 1) as i32);
    ranked_hands
}

fn card_strength(card: char, joker: bool) -> i32 {
    match card {
        'A' => 14,
        'K' => 13,
        'Q' => 12,
        'J' => {
            if joker {
                1
            } else {
                11
            }
        }
        'T' => 10,
        '9' => 9,
        '8' => 8,
        '7' => 7,
        '6' => 6,
        '5' => 5,
        '4' => 4,
        '3' => 3,
        '2' => 2,
        _ => 0,
    }
}

#[derive(Clone, Debug)]
struct Hand {
    cards: String,
    bid: i32,
    rank: i32,
}

impl Hand {
    fn calculate_score(&mut self, joker: bool) -> Hand {
        println!("JOKER TIEM: {}", joker);
        let char_count = utils::string_to_char_count_hashmap(&self.cards);
        if char_count.len() == 1
            || (joker && char_count.len() == 2 && *char_count.get(&'J').unwrap_or(&0) >= 1)
        {
            self.rank = 7;
        }
        if char_count.len() == 2 && utils::char_count_hashmap_max(&char_count) == 4
            || (joker && char_count.len() >= 3 && *char_count.get(&'J').unwrap_or(&0) >= 1)
        {
            self.rank = 6;
        }
        if char_count.len() == 2
            && utils::char_count_hashmap_max(&char_count) == 3
            && utils::char_count_hashmap_min(&char_count) == 2
            || (joker && char_count.len() == 3 && *char_count.get(&'J').unwrap_or(&0) == 1)
        {
            self.rank = 5;
        }
        if (char_count.len() == 3) && utils::char_count_hashmap_max(&char_count) == 3
            || (joker && char_count.len() == 4 && *char_count.get(&'J').unwrap_or(&0) == 1)
        {
            self.rank = 4;
        }
        if (char_count.len() == 3) && utils::char_count_hashmap_max(&char_count) == 2
            || (joker && char_count.len() == 4 && *char_count.get(&'J').unwrap_or(&0) == 1)
        {
            self.rank = 3;
        }
        if (char_count.len() == 4) && utils::char_count_hashmap_max(&char_count) == 2
            || (joker && *char_count.get(&'J').unwrap_or(&0) == 2)
        {
            self.rank = 2;
        }
        if (char_count.len() == 5) && utils::char_count_hashmap_max(&char_count) == 1
            || (joker && *char_count.get(&'J').unwrap_or(&0) == 1)
        {
            self.rank = 1;
        }
        self.clone()
    }
}
