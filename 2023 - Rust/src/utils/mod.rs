use std::fs;

pub fn read_file(filepath: String) -> String {
    match fs::read_to_string(filepath) {
        Ok(contents) => contents,
        Err(err) => format!("Error reading the file: {}", err),
    }
}

pub fn string_to_vec_i64(string: &str) -> Vec<i64> {
    string
        .trim()
        .split(' ')
        .collect::<Vec<_>>()
        .iter()
        .filter(|l| !l.is_empty())
        .map(|n| n.parse::<i64>().unwrap())
        .collect()
}

pub fn string_to_single_i64(string: &str) -> i64 {
    string
        .trim()
        .split(' ')
        .filter(|l| !l.is_empty())
        .collect::<Vec<_>>()
        .join("")
        .parse::<i64>()
        .unwrap()
}
