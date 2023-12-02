use regex::Regex;
use substring::Substring;

mod utils;

fn main() {
    let filepath: &str = "src/inputs/aoc02.txt";

    let contents: &str = &utils::read_file(filepath.to_string());

    let lines = contents.split('\n');

    let games: Vec<Game> = lines
        .clone()
        .filter(|l| !l.is_empty())
        .map(|game| Game {
            id: game
                .split_once(':')
                .unwrap()
                .0
                .to_string()
                .substring(5, 10)
                .parse::<i32>()
                .unwrap(),
            sub_games: game
                .split_once(':')
                .unwrap()
                .1
                .to_string()
                .split(';')
                .map(|sg| Subgame {
                    red: Regex::new(r"\d* red")
                        .unwrap()
                        .find_iter(sg)
                        .map(|s| {
                            s.as_str()
                                .split(' ')
                                .next()
                                .unwrap()
                                .parse::<i32>()
                                .unwrap()
                        })
                        .sum(),
                    blue: Regex::new(r"\d* blue")
                        .unwrap()
                        .find_iter(sg)
                        .map(|s| {
                            s.as_str()
                                .split(' ')
                                .next()
                                .unwrap()
                                .parse::<i32>()
                                .unwrap()
                        })
                        .sum(),
                    green: Regex::new(r"\d* green")
                        .unwrap()
                        .find_iter(sg)
                        .map(|s| {
                            s.as_str()
                                .split(' ')
                                .next()
                                .unwrap()
                                .parse::<i32>()
                                .unwrap()
                        })
                        .sum(),
                })
                .collect(),
        })
        .collect();

    let star1: i32 = games
        .iter()
        .filter(|g| g.can_game_exist(12, 13, 14))
        .map(|g| g.id)
        .sum();

    let star2: i32 = games.iter().map(|g| g.calculate_lowest_cubes()).sum();

    println!("Day 2 ⭐1️⃣  result: {}", star1);
    println!("Day 2 ⭐2️⃣  result: {}", star2);
}

struct Game {
    id: i32,
    sub_games: Vec<Subgame>,
}

struct Subgame {
    red: i32,
    green: i32,
    blue: i32,
}

impl Subgame {
    fn game_game_exist(&self, max_red: i32, max_green: i32, max_blue: i32) -> bool {
        if max_red >= self.red && max_green >= self.green && max_blue >= self.blue {
            return true
        }
        false
    }
}

impl Game {
    fn can_game_exist(&self, max_red: i32, max_green: i32, max_blue: i32) -> bool {
        self
            .sub_games
            .iter()
            .all(|sg| sg.game_game_exist(max_red, max_green, max_blue))
    }

    fn calculate_lowest_cubes(&self) -> i32 {
        let lowest_red = self.sub_games.iter().map(|sg| sg.red).max().unwrap();
        let lowest_green = self.sub_games.iter().map(|sg| sg.green).max().unwrap();
        let lowest_blue = self.sub_games.iter().map(|sg| sg.blue).max().unwrap();
        lowest_red * lowest_green * lowest_blue
    }
}
