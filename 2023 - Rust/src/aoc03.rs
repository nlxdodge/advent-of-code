// use regex::Regex;
// use substring::Substring;
use std::collections::HashSet;

mod utils;

fn main() {
    let day = 3;
    let filepath = format!("src/inputs/aoc{:0>2}.txt", day);
    let contents = &utils::read_file(filepath.to_string());

    let lines: Vec<&str> = contents.lines().collect();

    let mut engine = vec![vec![' '; lines.len()]; lines.len()];
    let mut star1 = 0;

    for (y, line) in lines.iter().enumerate() {
        for (x, c) in line.chars().enumerate() {
            engine[y][x] = c;
        }
    }
    print_engine(&engine);

    for y in 0..engine.len() {
        let mut has_surrounding = false;
        let mut number = "".to_owned();
        for x in 0..engine[0].len() {
            if !number.is_empty() && !engine[y][x].is_numeric() {
                if has_surrounding {
                    star1 += number.parse::<i32>().unwrap();
                    // println!(
                    //     "Adding: {} -> Total: {}",
                    //     number.parse::<i32>().unwrap(),
                    //     star1
                    // );
                } else {
                    // println!("Is not a part: {}", number.parse::<i32>().unwrap());
                }
                number = "".to_owned();
                has_surrounding = false;
            }
            if engine[y][x].is_numeric() {
                if !has_surrounding {
                    has_surrounding = check_surrounding(&engine, y, x);
                }
                number = format!("{number}{}", engine[y][x]);
            }
        }
    }

    println!("Day {day} ⭐1️⃣  result: {star1}");
    // println!("Day {day} ⭐2️⃣  result: {star2}");
}

fn check_surrounding(engine: &[Vec<char>], x: usize, y: usize) -> bool {
    let chars = ['@', '#', '$', '%', '&', '*', '-', '+', '=', '#', '/'];
    let height = engine.len();
    let width = engine[0].len();

    for i in (x as i32 - 1)..=(x as i32 + 1) {
        for j in (y as i32 - 1)..=(y as i32 + 1) {
            if i >= 0
                && i < height as i32
                && j >= 0
                && j < width as i32
                && !(i == x as i32 && j == y as i32)
                && chars.contains(&engine[i as usize][j as usize])
            {
                return true;
            }
        }
    }
    false
}

fn print_engine(engine: &Vec<Vec<char>>) {
    let mut chars = HashSet::new();
    for row in engine {
        for &c in row {
            // print!("{}", c);
            chars.insert(c);
        }
        // println!();
    }
    chars.iter().for_each(|c| println!("{c}"));
}
