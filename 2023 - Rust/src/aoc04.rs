use std::collections::HashMap;

mod utils;

fn main() {
    let day = 4;
    let filepath = format!("src/inputs/aoc{:0>2}.txt", day);
    let contents = &utils::read_file(filepath.to_string());
    let lines: Vec<&str> = contents.lines().collect();

    let mut games: Vec<Card> = lines
        .iter()
        .map(|l| Card {
            id: l
                .split_once(':')
                .unwrap()
                .0
                .chars()
                .filter(|c| c.is_numeric())
                .collect::<Vec<_>>()
                .iter()
                .collect::<String>()
                .parse::<i32>()
                .unwrap(),
            winning: l
                .split_once(':')
                .unwrap()
                .1
                .split_once(" | ")
                .unwrap()
                .0
                .to_string()
                .trim()
                .replace("  ", " ")
                .split(' ')
                .map(|n| n.parse::<i32>().unwrap())
                .collect(),
            numbers: l
                .split_once(':')
                .unwrap()
                .1
                .split_once(" | ")
                .unwrap()
                .1
                .to_string()
                .trim()
                .replace("  ", " ")
                .split(' ')
                .map(|n| n.trim().parse::<i32>().unwrap())
                .collect(),
            count: 1,
        })
        .collect();

    let star1: i32 = games.iter().map(|c| c.calculate_points()).sum();

    let mut duplicate_cards_set: HashMap<i32, i32> = HashMap::new();
    games.iter_mut().for_each(|c| {
        if duplicate_cards_set.contains_key(&c.id) {
            c.count += *duplicate_cards_set.get(&c.id).unwrap();
        }
        for card_id in &mut c.calculate_duplicate_card_ids() {
            duplicate_cards_set
                .entry(*card_id)
                .and_modify(|n| *n += c.count)
                .or_insert(c.count);
        }
    });

    let star2: i32 = games.iter().map(|c| c.count).sum();

    println!("Day {day} ⭐1️⃣  result: {star1}");
    println!("Day {day} ⭐2️⃣  result: {star2}");
}

struct Card {
    id: i32,
    winning: Vec<i32>,
    numbers: Vec<i32>,
    count: i32,
}

impl Card {
    fn calculate_duplicate_card_ids(&self) -> Vec<i32> {
        let count = self.get_matching_number_count();
        (self.id + 1..=self.id + count).collect()
    }

    fn get_matching_number_count(&self) -> i32 {
        self.numbers
            .iter()
            .filter(|n| self.winning.contains(n))
            .count() as i32
    }

    fn calculate_points(&self) -> i32 {
        let count_of_winning_numbers = self.get_matching_number_count();
        let mut points = 0;
        if count_of_winning_numbers != 0 {
            points += 1;
            for _x in 1..count_of_winning_numbers {
                points *= 2
            }
        }
        points
    }
}
