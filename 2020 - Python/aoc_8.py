from helpers.helpers import read_input
input = read_input("input_files/aoc_8_input.txt", '\n')

instructions = input

ran_instructions = list()
accumulator = 0
instruction_pointer = 0


def clear_globals():
    global accumulator, ran_instructions, instruction_pointer
    accumulator = 0
    ran_instructions = list()
    instruction_pointer = 0


def do_clear_run():
    global instructions, accumulator, ran_instructions, instruction_pointer
    clear_globals()
    return parse_instruction(instructions[0])


def parse_instruction(instruction):
    global instructions, accumulator, ran_instructions, instruction_pointer
    operator = instruction[4:5]
    variable = instruction[5:]

    if instruction_pointer in ran_instructions:
        return False

    ran_instructions.append(instruction_pointer)

    # accumulate the given instruction to the global
    if "acc" in instruction:
        accumulator = accumulator + eval(operator + variable)
        instruction_pointer += 1
        return parse_instruction(instructions[instruction_pointer])

    # jump to a given instruction in the instructions list
    elif "jmp" in instruction:
        instruction_pointer = instruction_pointer + eval(operator + variable)

        # when the pointer doesn't exist the program is complete and the correct value has been found
        if instruction_pointer == len(instructions):
            return True
        return parse_instruction(instructions[instruction_pointer])

    # for now this is a no operation, just continue to next instruction
    elif "nop" in instruction:
        instruction_pointer += 1
        return parse_instruction(instructions[instruction_pointer])


# begin from the first instruction to parse
parse_instruction(instructions[0])
print("Result 1: {}".format(accumulator))


# begin with the first instruction every time and reset when false
for index in range(0, len(input) - 1):
    instruction = input[index]

    # reset the input to clean state
    instructions = input

    if "nop" in instruction:
        input[index] = "jmp" + instruction[3:]
        if do_clear_run():
            print("Result 2: {}".format(accumulator))
        else:
            continue
    elif "jmp" in instruction:
        input[index] = "nop" + instruction[3:]
        if do_clear_run():
            print("Result 2: {}".format(accumulator))
        else:
            continue
