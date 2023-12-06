use rayon::prelude::*;

mod utils;

fn main() {
    let day = 5;
    let filepath = format!("src/inputs/aoc{:0>2}.txt", day);
    let contents = &utils::read_file(filepath.to_string());
    let lines: Vec<&str> = contents.lines().collect();

    let seeds = &utils::string_to_vec_i64(lines.first().unwrap().split_once(':').unwrap().1);
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
            let numbers = &utils::string_to_vec_i64(line);
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

    println!("Starting {:?}", chrono::offset::Local::now());
    let star1: i64 = seeds.iter().map(|s| seed_to_location(s, &maps)).min().unwrap();
    let star2: i64 = calculate_seed_ranges(seeds, &maps);
    println!("Day {day} ⭐1️⃣  result: {star1}");
    println!("Day {day} ⭐2️⃣  result: {star2}");
    println!("Ending {:?}", chrono::offset::Local::now());
}

fn seed_to_location(seed: &i64, maps: &Vec<Map>) -> i64 {
    let mut spot = *seed;
    let mut current_map = "seed";
    for map in maps {
        if map.from == current_map {
            for range in &map.ranges {
                if range.is_in_range(spot) {
                    spot = range.calculate_destination(spot);
                    break;
                }
            }
            current_map = &map.to;
        }
    }
    spot
}

fn calculate_seed_ranges(initial_seeds: &Vec<i64>, maps: &Vec<Map>) -> i64 {
    initial_seeds.par_chunks(2)
        .map(|pair| {
            *pair.first().unwrap()..(pair.first().unwrap() + pair.get(1).unwrap())
        })
        .map(|range| {
            range.map(|number| seed_to_location(&number, maps)).min().unwrap()
        })
        .min()
        .unwrap()
}

struct Map {
    from: String,
    to: String,
    ranges: Vec<Range>,
}

struct Range {
    destination_range_start: i64,
    source_range_start: i64,
    range: i64,
}

impl Range {
    fn is_in_range(&self, input: i64) -> bool {
        input >= self.source_range_start && input < self.source_range_start + self.range
    }

    fn calculate_destination(&self, input: i64) -> i64 {
        if input >= self.source_range_start && input < self.source_range_start + self.range {
            return input + (self.destination_range_start - self.source_range_start);
        }
        input
    }
}
