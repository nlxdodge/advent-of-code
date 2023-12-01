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
    let mut string_number = String::new();
    for chr in line.chars() {
        if chr.is_alphabetic() && text_search {
            string_number = string_number + &chr.to_string();
            if string_number.contains("one") {
                return 1;
            }
            if string_number.contains("two") {
                return 2;
            }
            if string_number.contains("three") {
                return 3;
            }
            if string_number.contains("four") {
                return 4;
            }
            if string_number.contains("five") {
                return 5;
            }
            if string_number.contains("six") {
                return 6;
            }
            if string_number.contains("seven") {
                return 7;
            }
            if string_number.contains("eight") {
                return 8;
            }
            if string_number.contains("nine") {
                return 9;
            }
        }
        if chr.is_numeric() {
            return chr.to_string().parse::<i32>().unwrap();
        }
    }
    return 0;
}

fn find_last_number(line: String, text_search: bool) -> i32 {
    let mut string_number = String::new();
    for chr in line.chars().rev() {
        if chr.is_alphabetic() && text_search {
            string_number = string_number + &chr.to_string();
            if string_number.contains("eno") {
                return 1;
            }
            if string_number.contains("owt") {
                return 2;
            }
            if string_number.contains("eerht") {
                return 3;
            }
            if string_number.contains("ruof") {
                return 4;
            }
            if string_number.contains("evif") {
                return 5;
            }
            if string_number.contains("xis") {
                return 6;
            }
            if string_number.contains("neves") {
                return 7;
            }
            if string_number.contains("thgie") {
                return 8;
            }
            if string_number.contains("enin") {
                return 9;
            }
        }
        if chr.is_numeric() {
            return chr.to_string().parse::<i32>().unwrap();
        }
    }
    return 0;
}
