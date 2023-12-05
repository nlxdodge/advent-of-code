mod utils;

fn main() {
    let day = 5;
    let filepath = format!("src/inputs/aoc{:0>2}.txt", day);
    let contents = &utils::read_file(filepath.to_string());
    let lines: Vec<&str> = contents.lines().collect();

    let seeds = &utils::string_to_vec_i32(lines.first().unwrap().split_once(':').unwrap().1);
    println!("Seeds: {:?}", seeds);

    let mut maps: Vec<Map> = vec![];
    let mut ranges: Vec<Range> = vec![];
    let mut from: &str = "";
    let mut to: &str = "";

    for (index, line) in lines.iter().enumerate() {
        if line.is_empty() || index == 0 {
            continue;
        }
        if line.chars().next().unwrap().is_alphabetic() {
            if !from.is_empty() {
                maps.push(Map {
                    from: from.to_string(),
                    to: to.to_string(),
                    ranges,
                });
            }
            ranges = vec![];

            from = line.split_once(' ').unwrap().0.split('-').next().unwrap();
            to = line.split_once(' ').unwrap().0.split('-').nth(2).unwrap();
        }
        if line.chars().next().unwrap().is_numeric() {
            let numbers = &utils::string_to_vec_i32(line);
            ranges.push(Range {
                destination_range_start: *numbers.first().unwrap(),
                source_range_start: *numbers.get(1).unwrap(),
                range: *numbers.get(2).unwrap(),
            });
        }
    }
    maps.push(Map {
        from: from.to_string(),
        to: to.to_string(),
        ranges,
    });

    let star1: Vec<i32> = seeds.iter().map(|s| seed_to_location(s, &maps)).collect();

    println!("Day {day} ⭐1️⃣  result: {}", star1.iter().min().unwrap());
    // println!("Day {day} ⭐2️⃣  result: {star2}");
}

fn seed_to_location(seed: &i32, maps: &Vec<Map>) -> i32 {
    let mut spot = *seed;
    let mut current_map = "seed";
    for map in maps {
        if map.from == current_map {
            for range in &map.ranges {
                if range.is_in_range(spot) {
                    spot = range.calculate_destination(spot);
                }
            }
            // println!("{}: {}", &map.to, spot);
            current_map = &map.to;
        }
    }
    println!("--------------------------------");
    spot
}

struct Map {
    from: String,
    to: String,
    ranges: Vec<Range>,
}

struct Range {
    destination_range_start: i32,
    source_range_start: i32,
    range: i32,
}

impl Range {
    fn is_in_range(&self, input: i32) -> bool {
        if input >= self.source_range_start && input < self.source_range_start + self.range - 1 {
            println!(
                "{input} is between {} and {}",
                self.source_range_start,
                self.source_range_start + self.range - 1,
            );
        }
        input >= self.source_range_start && input < self.source_range_start + self.range - 1
    }

    fn calculate_destination(&self, input: i32) -> i32 {
        // println!(
        //     "new: {}",
        //     input + (self.destination_range_start - self.source_range_start)
        // );
        if input >= self.source_range_start && input < self.source_range_start + self.range - 1 {
            return input + (self.destination_range_start - self.source_range_start);
        }
        input
    }
}
