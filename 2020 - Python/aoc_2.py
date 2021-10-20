from helpers.helpers import read_input

input = read_input("input_files/aoc_2_input.txt")

def is_correct(rule, password):
    char = rule.split(" ")[1]
    rule_length = rule.split(" ")[0].split("-")
    return password.count(char) >= int(rule_length[0]) and password.count(char) <= int(rule_length[1])


def is_correct_official(rule, password):
    char = rule.split(" ")[1]  # read the char
    char_position = rule.split(" ")[0].split("-")  # split the rule on `-`
    char_position = list(map(int, char_position))  # parse to integers
    return (password[char_position[0]] == char and password[char_position[1]] != char) or (password[char_position[0]] != char and password[char_position[1]] == char)


correct_password = 0
for line in input:
    value = line.split(":")
    if is_correct(value[0], value[1]):
        correct_password += 1
print("Result 1: {}".format(correct_password))


correct_password_official = 0
for line in input:
    value = line.split(":")
    if is_correct_official(value[0], value[1]):
        correct_password_official += 1
print("Result 2: {}".format(correct_password_official))
