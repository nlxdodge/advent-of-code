#![allow(dead_code)]
use std::{collections::HashMap, fs};

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

pub fn string_to_char_count_hashmap(string: &str) -> HashMap<char, i32> {
    let mut char_map: HashMap<char, i32> = HashMap::new();
    for chr in string.chars() {
        char_map.entry(chr).and_modify(|c| *c += 1).or_insert(1);
    }
    char_map
}

pub fn char_count_hashmap_min(char_count: &HashMap<char, i32>) -> i32 {
    *char_count.iter().map(|t| t.1).min().unwrap()
}

pub fn char_count_hashmap_max(char_count: &HashMap<char, i32>) -> i32 {
    *char_count.iter().map(|t| t.1).max().unwrap()
}
