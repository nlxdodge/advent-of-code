mod utils;

fn main() {
    let mut star1: i32 = 0;
    let mut star2: i32 = 0;

    let filepath: &str = "src/inputs/aoc01.txt";

    let contents: &str = &utils::read_file(filepath.to_string());

    let lines = contents.split("\r\n");

    for line in lines {
        println!("{line}");
    }

    println!("Day {} ⭐1️⃣  result: {}", utils::day(), star1);
    println!("Day {} ⭐2️⃣  result: {}", utils::day(), star2);
}