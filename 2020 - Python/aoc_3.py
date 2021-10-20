from helpers.helpers import read_input

input = read_input("input_files/aoc_3_input.txt")


def is_tree(input):
    return input == "#"


def check_slope(x_var, y_var=1):
    x_pos = 0
    trees = 0
    for line in input[::y_var]:
        x_modulo = x_pos % 31
        if is_tree(line[x_modulo]):
            trees += 1
        x_pos += x_var
    return trees


print("Result 1: {}".format(check_slope(3)))


result = check_slope(1) * check_slope(3) * check_slope(5) * \
    check_slope(7) * check_slope(1, 2)
print("Result 2: {}".format(result))
