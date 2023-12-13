use std::collections::VecDeque;

mod utils;

fn main() {
    let day = 9;
    let filepath = format!("src/inputs/aoc{:0>2}.txt", day);
    let contents = &utils::read_file(filepath.to_string());
    let lines: VecDeque<&str> = contents.lines().collect();

    let sensory_data: Vec<SensoryData> = lines
        .iter()
        .map(|d| SensoryData {
            points: d.split(' ').map(|n| n.parse::<i32>().unwrap()).collect(),
        })
        .collect();

    let calculated_values: Vec<Vec<i32>> = sensory_data.iter().map(|sd| sd.calculate()).collect();
    let star1: i32 = calculated_values
        .iter()
        .map(|data| data.get(1).unwrap())
        .sum();
    let star2: i32 = calculated_values
        .iter()
        .map(|data| data.first().unwrap())
        .sum();

    println!("Day {day} ⭐1️⃣  result: {star1}");
    println!("Day {day} ⭐2️⃣  result: {star2}");
}

struct SensoryData {
    points: Vec<i32>,
}

impl SensoryData {
    fn calculate(&self) -> Vec<i32> {
        let mut last_history: Vec<i32> = vec![];
        let mut first_history: Vec<i32> = vec![];
        let mut input = self.points.clone();
        first_history.push(*input.first().unwrap());
        last_history.push(*input.last().unwrap());
        while get_diff(&input).iter().any(|n| n != &0) {
            input = get_diff(&input);
            first_history.push(*input.first().unwrap());
            last_history.push(*input.last().unwrap());
        }
        input = get_diff(&input);
        first_history.push(*input.first().unwrap());
        last_history.push(*input.last().unwrap());

        let mut last_first = 0;
        let mut first_calculated: Vec<i32> = vec![];
        for first_value in first_history.clone().iter().rev() {
            if last_first > *first_value {
                last_first = -first_value;
            } else {
                last_first = *first_value;
            }
            first_calculated.push(last_first);
            println!("Next in line is: {}", last_first);
        }

        println!("Total: {}", first_calculated.iter().sum::<i32>());
        vec![
            first_calculated.iter().sum(),
            last_history.iter().rev().sum(),
        ]
    }
}

fn get_diff(input: &[i32]) -> Vec<i32> {
    let mut new_input = vec![];
    for window in input.windows(2) {
        new_input.push(window[1] - window[0]);
    }
    new_input
}
