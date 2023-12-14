mod utils;

fn main() {
    let day = 10;
    let filepath = format!("src/inputs/aoc{:0>2}.txt", day);
    let contents = &utils::read_file(filepath.to_string());
    let lines: Vec<&str> = contents.lines().collect();

    let max_size = 10;
    let mut start_pos: Vec2D = Vec2D { x: 0, y: 0 };
    let mut grid: Vec<Vec<Pipe>> = vec![vec![Pipe { chr: '.', steps: 0 }; max_size]; max_size];

    for (index_y, y) in lines.iter().enumerate() {
        for (index_x, x) in y.chars().enumerate() {
            grid[index_x][index_y] = Pipe { chr: x, steps: 0 };
            if x == 'S' {
                start_pos.x = index_x;
                start_pos.y = index_y;
            }
        }
    }
    let mut current_pos = start_pos.clone();
    let mut neigbhours = check_for_valid_neighbor(&grid, &current_pos);

    println!("Day {day} ⭐1️⃣  result: {:?}", start_pos);
    // println!("Day {day} ⭐2️⃣  result: {star2}");
}

fn check_for_valid_neighbor(grid: &[Vec<Pipe>], current_pos: &Vec2D) -> Vec<Vec2D> {
    let mut neighbours: Vec<Vec2D> = vec![];
    let check_below = Vec2D {
        x: current_pos.x + 1,
        y: current_pos.y,
    };
    // Going down
    if check_for_chars(grid, &check_below, vec!['|', 'L', 'J']) {
        neighbours.push(check_below);
    }
    let check_right = Vec2D {
        x: current_pos.x + 1,
        y: current_pos.y,
    };
    // Going right
    if check_for_chars(grid, &check_right, vec!['-', 'J', '7']) {
        neighbours.push(check_right);
    }
    let check_right = Vec2D {
        x: current_pos.x - 1,
        y: current_pos.y,
    };
    // Going left
    if check_for_chars(grid, &check_right, vec!['-', 'F', 'L']) {
        neighbours.push(check_right);
    }
    let check_below = Vec2D {
        x: current_pos.x,
        y: current_pos.y + 1,
    };
    // Going Up
    if check_for_chars(grid, &check_below, vec!['F', '|', '7']) {
        neighbours.push(check_below);
    }
    neighbours
}

fn check_for_chars(grid: &[Vec<Pipe>], pos: &Vec2D, chrs: Vec<char>) -> bool {
    if let Some(row) = grid.get(pos.x - 1) {
        if let Some(column) = row.get(pos.y) {
            if chrs.contains(&column.chr) {
                return true;
            }
        }
    }
    false
}

#[derive(Clone, Debug)]
struct Vec2D {
    x: i32,
    y: i32,
}

#[derive(Clone, Debug)]
struct Pipe {
    chr: char,
    steps: i32,
}
