import math

from helpers.helpers import read_input

input = read_input("aoc_5_input.txt", '\n')

def parse_row(row_data):
    min_range = 0
    max_range = 127

    for char in row_data:
        if char == "F":
            max_range = math.floor(max_range / 2)
            print("Max: {}".format(max_range))
        if char == "B":
            min_range = math.floor(max_range / 2)
            print("Min: {}".format(min_range))
    print("Final Min: {}".format(min_range))
    print("Final Max: {}".format(max_range))




for seat in input:
    row_data = seat[0:7]
    row_data = "FBFBBFFRLR"
    seat_data = seat[7:10]
    print(row_data)
    print(seat_data)
    parse_row(row_data)

    exit()