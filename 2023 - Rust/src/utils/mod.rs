use std::fs;

pub fn read_file(filepath: String) -> String {
    match fs::read_to_string(filepath) {
        Ok(contents) => contents,
        Err(err) => format!("Error reading the file: {}", err),
    }
}

pub fn string_to_vec_i64(string: &str) -> Vec<i64> {
    return string
        .trim()
        .split(' ')
        .collect::<Vec<_>>()
        .iter()
        .map(|n| n.parse::<i64>().unwrap())
        .collect();
}
