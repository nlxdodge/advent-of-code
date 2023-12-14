mod utils;

fn main() {
    let day = 10;
    let filepath = format!("src/inputs/aoc{:0>2}.txt", day);
    let contents = &utils::read_file(filepath.to_string());
    let lines: Vec<&str> = contents.lines().collect();

    let max_size = 9;
    let mut start_pos: Vec2D = Vec2D { x: 0, y: 0 };
    let mut grid: Vec<Vec<char>> = vec![vec!['.'; max_size]; max_size];

    for (index_y, y) in lines.iter().enumerate() {
        for (index_x, x) in y.chars().enumerate() {
            grid[index_x][index_y] = x;
            if x == 'S' {
                start_pos.x = index_x;
                start_pos.y = index_y;
            }
        }
    }
    let mut current_pos = start_pos.clone();
    check_for_valid_neighbor(&grid, &current_pos);

    println!("Day {day} ⭐1️⃣  result: {:?}", start_pos);
    // println!("Day {day} ⭐2️⃣  result: {star2}");
}

fn check_for_valid_neighbor(grid: &Vec<Vec<char>>, current_pos: &Vec2D) -> Vec<Vec2D> {
    let mut neighbours: Vec<Vec2D> = vec![];
    let check_below = Vec2D {
        x: current_pos.x + 1,
        y: current_pos.y,
    };
    if check_for_char(grid, &check_below, '|') {
        neighbours.push(check_below);
    }
    let check_right = Vec2D {
        x: current_pos.x + 1,
        y: current_pos.y,
    };
    if check_for_char(grid, &check_right, '-') {
        neighbours.push(check_right);
    }

    let check_right = Vec2D {
        x: current_pos.x - 1,
        y: current_pos.y,
    };
    if check_for_char(grid, &check_right, '-') {
        neighbours.push(check_right);
    }
    if grid[current_pos.x][current_pos.y + 1] == '-' {
        neighbours.push(Vec2D {
            x: current_pos.x,
            y: current_pos.y + 1,
        })
    }

    let check_below = Vec2D {
        x: current_pos.x,
        y: current_pos.y + 1,
    };
    if check_for_char(grid, &check_below, 'F') {
        neighbours.push(check_below);
    }

    println!(
        "Something: {} @ {} {}",
        grid[current_pos.x][current_pos.y + 1],
        current_pos.x,
        current_pos.y + 1
    );
    neighbours
}

fn check_for_char(grid: &[Vec<char>], pos: &Vec2D, chr: char) -> bool {
    if let Some(row) = grid.get(pos.x - 1) {
        if let Some(column) = row.get(pos.y) {
            if column == &chr {
                return true;
            }
        }
    }
    false
}

#[derive(Clone, Debug)]
struct Vec2D {
    x: usize,
    y: usize,
}
