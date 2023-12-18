mod utils;

fn main() {
    let day = 3;
    let filepath = format!("src/inputs/aoc{:0>2}.txt", day);
    let contents = &utils::read_file(filepath.to_string());
    let lines: Vec<&str> = contents.lines().collect();

    let mut engine = vec![vec![' '; lines.len()]; lines.len()];
    let mut star1 = 0;

    for (y, line) in lines.iter().enumerate() {
        for (x, c) in line.chars().enumerate() {
            engine[y][x] = c;
        }
    }

    for y in 0..engine.len() {
        let mut has_surrounding = false;
        let mut number = "".to_owned();
        for x in 0..engine[0].len() {
            if !number.is_empty() && !engine[y][x].is_ascii_digit() {
                if has_surrounding {
                    star1 += number.parse::<i32>().unwrap();
                }
                number = "".to_owned();
                has_surrounding = false;
            }
            if engine[y][x].is_ascii_digit() {
                if !has_surrounding {
                    has_surrounding = check_surrounding(&engine, y, x);
                }
                number = format!("{number}{}", engine[y][x]);
            }
            if x == engine.len() - 1 && has_surrounding {
                star1 += number.parse::<i32>().unwrap();
            }
        }
    }

    let mut star2 = 0;
    for y in 0..engine.len() {
        for x in 0..engine.len() {
            if engine[x][y] == '*' {
                star2 += calculate_gear_ratio(&engine, x, y);
            }
        }
    }

    println!("Day {day} ⭐1️⃣  result: {star1}");
    println!("Day {day} ⭐2️⃣  result: {star2}");
}

fn calculate_gear_ratio(engine: &[Vec<char>], x: usize, y: usize) -> i32 {
    let mut complete_parts = get_surrounding_numbers(engine, x, y);
    complete_parts.dedup_by(|a, b| a.equals(b));
    if complete_parts.len() == 2 {
        return complete_parts
            .iter()
            .map(|c| c.part_number())
            .product::<i32>();
    }
    0
}

fn get_surrounding_numbers(engine: &[Vec<char>], x: usize, y: usize) -> Vec<Part> {
    let height = engine.len();
    let width = engine[0].len();
    let mut complete_parts: Vec<Part> = vec![];

    for sub_x in (x as i32 - 1)..=(x as i32 + 1) {
        for sub_y in (y as i32 - 1)..=(y as i32 + 1) {
            if sub_x >= 0
                && sub_x < height as i32
                && sub_y >= 0
                && sub_y < width as i32
                && !(sub_x == x as i32 && sub_y == y as i32)
                && engine[sub_x as usize][sub_y as usize].is_numeric()
            {
                let mut parts: Vec<PartShard> = vec![];
                let iter_x = sub_x as usize;
                let mut iter_y = sub_y as usize;
                parts.push(PartShard {
                    x: iter_x as i32,
                    y: iter_y as i32,
                    chr: engine[iter_x][iter_y],
                });
                while iter_y + 1 < engine.len() && engine[iter_x][iter_y + 1].is_numeric() {
                    iter_y += 1;
                    parts.push(PartShard {
                        x: iter_x as i32,
                        y: iter_y as i32,
                        chr: engine[iter_x][iter_y],
                    });
                }
                iter_y = sub_y as usize;
                while iter_y as i32 > 0 && engine[iter_x][iter_y - 1].is_numeric() {
                    iter_y -= 1;
                    parts.splice(
                        ..0,
                        vec![PartShard {
                            x: iter_x as i32,
                            y: iter_y as i32,
                            chr: engine[iter_x][iter_y],
                        }],
                    );
                }
                complete_parts.push(Part { parts });
            }
        }
    }
    complete_parts
}

fn check_surrounding(engine: &[Vec<char>], x: usize, y: usize) -> bool {
    let height = engine.len();
    let width = engine[0].len();

    for i in (x as i32 - 1)..=(x as i32 + 1) {
        for j in (y as i32 - 1)..=(y as i32 + 1) {
            if i >= 0
                && i < height as i32
                && j >= 0
                && j < width as i32
                && !(i == x as i32 && j == y as i32)
                && engine[i as usize][j as usize] != '.'
                && !engine[i as usize][j as usize].is_numeric()
            {
                return true;
            }
        }
    }
    false
}

struct Part {
    parts: Vec<PartShard>,
}

impl Part {
    fn part_number(&self) -> i32 {
        self.parts
            .iter()
            .map(|p| p.chr)
            .collect::<String>()
            .parse::<i32>()
            .unwrap()
    }

    fn equals(&self, other: &mut Part) -> bool {
        self.parts.iter().all(|p| other.parts.contains(p))
    }
}

#[derive(PartialEq)]
struct PartShard {
    x: i32,
    y: i32,
    chr: char,
}
