from helpers.helpers import read_input
cypher = read_input("input_files/aoc_9_input.txt", '\n')

# clean data to int array
cypher = [int(x) for x in cypher]

buffer_length = 25
buffer = cypher[:buffer_length]

result1 = 0


def valid(cypher, index, buffer):
    to_find = cypher[index]

    for x_index, x in enumerate(buffer):
        for y_index, y in enumerate(buffer):
            if y_index is not x_index and int(x + y) == int(to_find):
                return True
    return False


def find_numbers(cypher, start):
    sum_array = []
    for index in range(start, len(cypher)):
        sum_array.append(cypher[index])
        if int(sum(sum_array)) == int(result1):
            return sum_array
        if sum(sum_array) > result1:
            sum_array.clear()
            break


# first Result calculation find wrong number
for index in range(buffer_length, len(cypher)):
    if not valid(cypher, index, buffer):
        print("Result 1: {}".format(cypher[index]))
        result1 = cypher[index]
        break
    buffer = [int(x) for x in cypher[index + 1 - buffer_length:index + 1]]


for index in range(0, len(cypher)):
    sum_array = find_numbers(cypher, index)
    if sum_array:
        sum_array.sort()
        print("Result 2: {}".format(sum_array[0] + sum_array[-1]))
        break
