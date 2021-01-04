from helpers.helpers import read_input

input = read_input("aoc_5_input.txt", '\n')


def parse_row(row_data):
    rows = list(range(128))
    for char in row_data:
        if char == "B":
            rows = rows[round(len(rows) / 2):]
        if char == "F":
            rows = rows[:round(len(rows) / 2)]
    return rows[0]

def parse_column(column_data):
    rows = list(range(8))
    for char in column_data:
        if char == "R":
            rows = rows[round(len(rows) / 2):]
        if char == "L":
            rows = rows[:round(len(rows) / 2)]
    return rows[0]

def calculate_seat_id(row, column):
    return row * 8 + column

highest_seat_id = 0
seat_ids = []

for seat in input:
    row_data = seat[0:7]
    column_data = seat[7:10]
    row = parse_row(row_data)
    column = parse_column(column_data)
    seat_id = calculate_seat_id(row, column)
    seat_ids.append(seat_id)
    if highest_seat_id < seat_id:
        highest_seat_id = seat_id
print("Result 1: {}".format(highest_seat_id))


seat_ids.sort()

found_seat_id = 0
last_seat_id = seat_ids[0] - 1

for seat_id in seat_ids:
    if not (seat_id + 1 == last_seat_id or seat_id - 1 == last_seat_id):
        found_seat_id = seat_id
        break
    last_seat_id = seat_id

print("Result 2: {}".format(found_seat_id - 1))


