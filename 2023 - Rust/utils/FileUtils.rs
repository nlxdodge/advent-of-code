use std::fs;

struct FileUtils {

}

impl FileUtils {
    fn readfile(filepath: String) -> String {
        match fs::read_to_string("example.txt") {
            Ok(contents) => {
                return contents
            }
            Err(err) => {
                return format!("Error reading the file: {}", err)
            }
        }
    }
}

