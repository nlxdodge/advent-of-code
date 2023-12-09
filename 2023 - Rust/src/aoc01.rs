use std::collections::HashMap;
use substring::Substring;

mod utils;

fn main() {
    let filepath: &str = "src/inputs/aoc01.txt";

    let contents: &str = &utils::read_file(filepath.to_string());

    let lines = contents.split("\n");

    let star1: i32 = lines
        .clone()
        .map(|f| {
            (find_first_number(f.to_string(), false).to_string()
                + &find_last_number(f.to_string(), false).to_string())
                .parse::<i32>()
                .unwrap()
        })
        .sum();
    let star2: i32 = lines
        .clone()
        .map(|f| {
            (find_first_number(f.to_string(), true).to_string()
                + &find_last_number(f.to_string(), true).to_string())
                .parse::<i32>()
                .unwrap()
        })
        .sum();

    println!("Day 1 ⭐1️⃣  result: {}", star1);
    println!("Day 1 ⭐2️⃣  result: {}", star2);
}

fn find_first_number(line: String, text_search: bool) -> i32 {
    let number_mapping = HashMap::from([
        ("one", 1),
        ("two", 2),
        ("three", 3),
        ("four", 4),
        ("five", 5),
        ("six", 6),
        ("seven", 7),
        ("eight", 8),
        ("nine", 9),
    ]);
    let mut string_number = String::new();
    for chr in line.chars() {
        if chr.is_alphabetic() && text_search {
            string_number = string_number + &chr.to_string();
            for (k, v) in number_mapping {
                if string_number.contains(k) {
                    return v;
                }
            }
        }
        if chr.is_numeric() {
            return chr.to_string().parse::<i32>().unwrap();
        }
    }
    return 0;
}

fn find_last_number(line: String, text_search: bool) -> i32 {
    let number_mapping = HashMap::from([
        ("eno", 1),
        ("owt", 2),
        ("eerht", 3),
        ("ruof", 4),
        ("evif", 5),
        ("xis", 6),
        ("neves", 7),
        ("thgie", 8),
        ("enin", 9),
    ]);
    let mut string_number = String::new();
    for index in 0..line.len() {
        if line.char_at(index).is_alphabetic() && text_search {
            println!(line.substring(index, index + 4));
            for (k, v) in line.substring(index, index + 4) {
                if string_number.contains(k) {
                    return v;
                }
            }
        }
        if line.char_at(index).to_string().is_numeric() {
            return line.char_at(index).to_string().parse::<i32>().unwrap();
        }
    }
    return 0;
}
