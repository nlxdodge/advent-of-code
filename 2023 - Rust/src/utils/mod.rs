use std::fs;
use std::path::Path;

pub fn day() -> String {
    let this_file = file!();
    let file_name = Path::new(this_file)
        .file_name()
        .and_then(|s| s.to_str())
        .unwrap();
    let char_one: char = file_name.chars().nth(3).unwrap_or('0');
    let char_two: char = file_name.chars().nth(4).unwrap_or('0');

    format!("{}{}", char_one, char_two)
}

pub fn read_file(filepath: String) -> String {
    match fs::read_to_string(filepath) {
        Ok(contents) => contents,
        Err(err) => format!("Error reading the file: {}", err)
    }
}
