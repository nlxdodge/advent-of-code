use std::fs;

pub fn read_file(filepath: String) -> String {
    match fs::read_to_string(filepath) {
        Ok(contents) => contents,
        Err(err) => format!("Error reading the file: {}", err),
    }
}
