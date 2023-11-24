use md5::{Md5, Digest};

fn main() {
    let input = "iwrupvqb";
    let variable = 1;
    let max_variable = 9999999;
    let mut found5 = false;
    let mut found6 = false;

    for number in variable..max_variable {
        let combine = format!("{}{}", input, number);
        let hash = hash(&combine);

        if check_for_x_zeros(&hash, 5) && !found5 {
            println!("Lowest positive number with 5 zeros: {}", number);
            found5 = true;
        }
        if check_for_x_zeros(&hash, 6) && !found6 {
            println!("Lowest positive number with 6 zeros: {}", number);
            found6 = true;
        }
        if found5 && found6 {
            break;
        }
    }
}

fn hash(input: &str) -> String {
    let hash = Md5::digest(input.as_bytes());
    return format!("{:x}", hash);
}

fn check_for_x_zeros(input: &str, size: i32) -> bool {
    let mut counter = 0;
    for chr in input.chars() {
        if chr == '0' {
            counter += 1;
        } else {
            return false;
        }
        if counter == size {
            return true;
        }
    }
    return false;
}