use crate::utils;

pub fn run() {
    let day = 11;
    let filepath = format!("src/inputs/aoc{:0>2}.txt", day);
    let contents = utils::read_file(filepath.to_string());
    let lines: Vec<&str> = contents.lines().collect();

    let mut universe = vec![vec!['.'; lines.len()]; lines.len()];

    // load the universe with data
    for x in 0..universe.len() {
        for y in 0..universe.len() {
            universe[x][y] = lines.get(x).unwrap().chars().nth(y).unwrap();
        }
    }

    // expand the universe
    universe = expand_univese(universe);

    println!("TEST");
    // println!("Day {day} ⭐1️⃣  result: {star1}");
    // println!("Day {day} ⭐2️⃣  result: {star2}");
}

fn expand_univese(universe: Vec<Vec<char>>) -> Vec<Vec<char>> {
    let mut expandable_universe = universe.clone();

    // check horizontal
    for x in universe.iter() {
        expandable_universe.push(x.clone());
        if x.iter().all(|p| p == &'.') {
            expandable_universe.push(x.clone());
        }
    }

    for x in 0..universe.len() {
        for y in 0..universe.len() {}
    }

    expandable_universe
}
