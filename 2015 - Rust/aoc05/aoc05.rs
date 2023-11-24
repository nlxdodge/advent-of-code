fn main() {
    let input = r#"aaaa
"#;

    let mut nice_strings = 0;
    let mut nice_strings_second = 0;

    for line in input.lines() {
        if is_nice(&line) {
            nice_strings += 1;
        }
        if is_nice_second(&line) {
            nice_strings_second += 1;
        } else {
            // println!("String invalid?: {}", &line);
        }
    }
    println!("Nice strings with first ruleset: {}", nice_strings);
    println!("Nice strings with second ruleset: {}", nice_strings_second);
}

fn is_nice(line: &str) -> bool {
    return has_vowles(&line) && has_double(&line) && !has_forbidden_string(&line);
}

fn has_vowles(line: &str) -> bool {
    let mut counter = 0;
    let vowles = "aeiou";
    for chr in line.chars() {
        if vowles.contains(chr) {
            counter += 1;
        }
    }
    return counter > 2;
}

fn has_double(line: &str) -> bool {
    let mut last_chr = '!';
    for chr in line.chars() {
        if chr == last_chr {
            return true;
        }
        last_chr = chr;
    }
    return false;
}

fn has_forbidden_string(line: &str) -> bool {
    return line.contains("ab") || line.contains("cd") || line.contains("pq") || line.contains("xy");
}

fn is_nice_second(line: &str) -> bool {
    return find_duplicate_double(&line) & find_repeating_with_irregular_in_between(&line);
}

fn find_duplicate_double(line: &str) -> bool {
    let mut doubles: Vec<String> = Vec::new();
    let mut last_double = 0;
    for window in line.chars().collect::<Vec<_>>().windows(2) {
        let combine = window.iter().collect::<String>();
        if doubles.contains(&combine) && combine != last_double {
            if combine == last_double {
                println!("Found a double one: {} double is: {}", line, combine);          
            }
            println!("In {} found combo: {}", line, combine);
            return true;
        }

        if let Some(index) = line.find(combine) {
            last_double = index;
        }


        last_double = line.char_at(combine);
        doubles.push(combine);
    }
    return false;
}

fn find_repeating_with_irregular_in_between(line: &str) -> bool {
    for window in line.chars().collect::<Vec<_>>().windows(3) {
        if window[0] == window[2] {
            // println!("In {}, found combo with irregular: {}{}{}", line, window[0], window[1], window[2]);
            return true;
        }
    }
    return false;
}