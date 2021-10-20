from helpers.helpers import read_input
input = read_input("input_files/aoc_6_input.txt", '\n\n')

yes_answers = list()
for answers in input:
    chars = list(answers.replace("\n", ""))
    uniques = list(dict.fromkeys(chars))
    yes_answers.append(len(uniques))

print("Result 1: {}".format(sum(yes_answers)))

yes_answers = list()
for answers in input:
    yes_counter = 0
    needed_yes_amount = len(answers.split("\n"))
    local_yes_answers = dict()
    for answer in answers.split("\n"):
        for char in answer:
            if char in local_yes_answers:
                local_yes_answers[char] += 1
            else:
                local_yes_answers[char] = 1

    for local_yes_count in local_yes_answers:
        if local_yes_answers[local_yes_count] == needed_yes_amount:
            yes_counter += 1

    yes_answers.append(yes_counter)
print("Result 2: {}".format(sum(yes_answers)))
    