use std::thread::current;

mod utils;

fn main() {
    let day = 10;
    let filepath = format!("src/inputs/aoc{:0>2}.txt", day);
    let contents = &utils::read_file(filepath.to_string());
    let lines: Vec<&str> = contents.lines().collect();

    let max_size = 10;
    let mut start_pos: Vec2D = Vec2D { x: 0, y: 0 };
    let mut grid: Vec<Vec<Pipe>> = vec![
        vec![
            Pipe {
                chr: '.',
                stepped_on: false,
                steps: 999
            };
            max_size
        ];
        max_size
    ];

    for (index_y, y) in lines.iter().enumerate() {
        for (index_x, x) in y.chars().enumerate() {
            grid[index_y][index_x] = Pipe {
                chr: x,
                stepped_on: false,
                steps: 999,
            };
            if x == 'S' {
                start_pos.x = index_x as i32;
                start_pos.y = index_y as i32;
            }
        }
    }

    let mut steps = 0;
    let mut neigbhours: Vec<Vec2D> = vec![];
    let mut current_pos = start_pos.clone();

    neigbhours.extend(check_for_valid_neighbor(&mut grid, &current_pos));
    while !neigbhours.is_empty() {
        steps += 1;
        current_pos = neigbhours.pop().unwrap();
        let new_neigbhours = check_for_valid_neighbor(&mut grid, &current_pos);
        for new_neigbhour in new_neigbhours.clone() {
            if grid[new_neigbhour.x as usize][new_neigbhour.y as usize].steps > steps {
                grid[new_neigbhour.x as usize][new_neigbhour.y as usize].steps = steps;
            }
        }
        neigbhours.extend(new_neigbhours);
    }
    print_step_grid(&grid);

    println!("Day {day} ⭐1️⃣  result: {:?}", start_pos);
    // println!("Day {day} ⭐2️⃣  result: {star2}");
}

fn print_step_grid(grid: &[Vec<Pipe>]) {
    for y in 0..grid.len() {
        for x in 0..grid.len() {
            print!("{: >3} ", grid[x][y].steps);
        }
        println!();
    }
}

fn check_for_valid_neighbor(grid: &mut [Vec<Pipe>], current_pos: &Vec2D) -> Vec<Vec2D> {
    let mut current_pos_clone = current_pos.clone();
    println!(
        "Current: x:{} y:{} = {}",
        current_pos.x, current_pos.y, grid[current_pos.x as usize][current_pos.y as usize].chr
    );
    let mut neighbours: Vec<Vec2D> = vec![];

    // Going Up
    current_pos_clone = current_pos.clone();
    let check_up = Vec2D {
        x: current_pos_clone.x - 1,
        y: current_pos_clone.y,
    };

    if check_for_chars(grid, &check_up, vec!['F', '|', '7']) {
        neighbours.push(check_up);
    }

    // Going right
    current_pos_clone = current_pos.clone();
    let check_right = Vec2D {
        x: current_pos_clone.x,
        y: current_pos_clone.y + 1,
    };
    if check_for_chars(grid, &check_right, vec!['-', 'J', '7']) {
        neighbours.push(check_right);
    }

    // Going down
    current_pos_clone = current_pos.clone();
    let check_below = Vec2D {
        x: current_pos_clone.x + 1,
        y: current_pos_clone.y,
    };
    if check_for_chars(grid, &check_below, vec!['|', 'L', 'J']) {
        neighbours.push(check_below);
    }

    // Going left
    current_pos_clone = current_pos.clone();
    let check_left = Vec2D {
        x: current_pos_clone.x,
        y: current_pos_clone.y - 1,
    };
    if check_for_chars(grid, &check_left, vec!['-', 'F', 'L']) {
        neighbours.push(check_left);
    }
    neighbours
}

fn check_for_chars(grid: &mut [Vec<Pipe>], pos: &Vec2D, chrs: Vec<char>) -> bool {
    if let Some(row) = grid.get(pos.x as usize) {
        if let Some(column) = row.get(pos.y as usize) {
            println!(
                "Pos: x:{} y:{} -> {} equals {:?}?",
                pos.x, pos.y, &column.chr, chrs
            );
            if chrs.contains(&column.chr) && !grid[pos.x as usize][pos.y as usize].stepped_on {
                grid[pos.x as usize][pos.y as usize].stepped_on = true;
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
    stepped_on: bool,
    steps: i32,
}
