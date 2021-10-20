from helpers.helpers import read_input

input = read_input("input_files/aoc_1_input.txt")
input = list(map(int, input))

found = False
for x in input:
    if found:
        break
    for y in input:
        if x + y == 2020:
            print("Result 1: {}".format(x * y))
            found = True
            break

found = False
for x in input:
    if found:
        break
    for y in input:
        if found:
            break
        for z in input:
            if x + y + z == 2020:
                print("Result 2: {}".format(x * y * z))
                found = True
                break
            