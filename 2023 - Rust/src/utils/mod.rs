use std::fs;
use std::path::Path;

pub fn day() -> String {
    let this_file = file!();
    let file_name = Path::new(this_file)
        .file_name()
        .and_then(|s| s.to_str())
        .unwrap();


    let char_one: char = match file_name.chars().nth(3) {
        None => '0',
        Some(d) => d
    };
    let char_two: char = match file_name.chars().nth(4) {
        None => '0',
        Some(d) => d
    };

    return format!("{}{}", char_one, char_two);
}

pub fn read_file(filepath: String) -> String {
    match fs::read_to_string(filepath) {
        Ok(contents) => return contents,
        Err(err) => return format!("Error reading the file: {}", err),
    }
}
