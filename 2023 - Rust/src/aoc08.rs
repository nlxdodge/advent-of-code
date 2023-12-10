use std::collections::VecDeque;

mod utils;

fn main() {
    let day = 8;
    let filepath = format!("src/inputs/aoc{:0>2}.txt", day);
    let contents = &utils::read_file(filepath.to_string());
    let mut lines: VecDeque<&str> = contents.lines().collect();

    let instruction = VecDeque::pop_front(&mut lines).unwrap();

    let nodes: Vec<Node> = lines
        .iter()
        .filter(|l| !l.is_empty())
        .map(|l| {
            let complete = l.replace(' ', "");
            Node {
                name: complete.split_once('=').unwrap().0.to_string(),
                left: complete
                    .split_once('=')
                    .unwrap()
                    .1
                    .split_once(',')
                    .unwrap()
                    .0
                    .replace('(', ""),
                right: complete
                    .split_once('=')
                    .unwrap()
                    .1
                    .split_once(',')
                    .unwrap()
                    .1
                    .replace(')', ""),
            }
        })
        .collect();

    let star1 = count_towards_z(
        &nodes,
        instruction.to_string(),
        "AAA".to_string(),
        "ZZZ".to_string(),
        false,
    );
    println!("Day {day} ⭐1️⃣  result: {star1}");

    let starting_nodes = &nodes
        .iter()
        .filter(|n| utils::string_ends_with_char(&n.name, 'A'))
        .collect::<Vec<_>>();
    let star2 = utils::lcm_vec(
        starting_nodes
            .iter()
            .map(|n| {
                count_towards_z(
                    &nodes,
                    instruction.to_string(),
                    n.name.clone(),
                    "Z".to_string(),
                    true,
                )
            })
            .map(|n| n.to_string().parse::<i64>().unwrap())
            .collect(),
    );
    println!("Day {day} ⭐2️⃣  result: {star2}");
}

fn find_node(nodes: &[Node], to_find: String) -> &Node {
    nodes.iter().find(|n| n.name == to_find).unwrap()
}

fn count_towards_z(
    nodes: &[Node],
    instruction: String,
    from: String,
    to_find: String,
    last_char: bool,
) -> i32 {
    let mut step_counter = 0;
    let mut instruction_counter = 0;
    let mut current_instruction = utils::string_nth_char(&instruction, instruction_counter);
    let mut current_node = find_node(nodes, from);
    while part_check(current_node.name.clone(), to_find.clone(), last_char) {
        step_counter += 1;
        if current_instruction == 'L' {
            current_node = find_node(nodes, current_node.left.clone())
        } else {
            current_node = find_node(nodes, current_node.right.clone())
        }
        instruction_counter += 1;
        if instruction_counter >= instruction.len() {
            instruction_counter = 0;
        }
        current_instruction = utils::string_nth_char(&instruction, instruction_counter);
    }
    step_counter
}

fn part_check(input: String, to_find: String, last_char: bool) -> bool {
    if last_char {
        input.chars().nth(2).unwrap().to_string() != to_find
    } else {
        input != to_find
    }
}

#[derive(Clone, Debug)]
struct Node {
    name: String,
    left: String,
    right: String,
}
