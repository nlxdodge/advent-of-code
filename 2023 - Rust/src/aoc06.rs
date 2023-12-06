mod utils;

fn main() {
    let day = 6;
    let filepath = format!("src/inputs/aoc{:0>2}.txt", day);
    let contents = &utils::read_file(filepath.to_string());
    let lines: Vec<&str> = contents.lines().collect();

    let mut races = vec![];
    let times = utils::string_to_vec_i64(lines.first().unwrap().split_once(':').unwrap().1);
    let distances = utils::string_to_vec_i64(lines.get(1).unwrap().split_once(':').unwrap().1);
    for index in 0..times.len() {
        races.push(Race {
            time: times[index],
            distance: distances[index],
        })
    }

    let single_race = Race {
        time: utils::string_to_single_i64(lines.first().unwrap().split_once(':').unwrap().1),
        distance: utils::string_to_single_i64(lines.get(1).unwrap().split_once(':').unwrap().1),
    };

    let star1 = races
        .iter()
        .map(|r| r.calculate_beat_times())
        .product::<i64>();
    let star2 = single_race.calculate_beat_times();

    println!("Day {day} ⭐1️⃣  result: {star1}");
    println!("Day {day} ⭐2️⃣  result: {star2}");
}

struct Race {
    time: i64,
    distance: i64,
}

impl Race {
    fn calculate_beat_times(&self) -> i64 {
        let mut winning_speeds: Vec<i64> = vec![];
        for speed in 1..self.time {
            if self.distance < speed * (self.time - speed) {
                winning_speeds.push(speed);
            }
        }
        winning_speeds.len() as i64
    }
}
