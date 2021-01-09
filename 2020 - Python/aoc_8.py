from helpers.helpers import read_input
instructions = read_input("aoc_8_input.txt", '\n')

ran_instructions = list()
accumulator = 0
instruction_pointer = 0


def parse_instruction(instruction):
    global accumulator, ran_instructions, instruction_pointer
    print(instruction)
    operator = instruction[4:5]
    variable = instruction[5:]

    print(ran_instructions)
    if instruction in ran_instructions:
        accumulator = 5
        return

    ran_instructions.append(instruction)

    # accumulate the given instruction to the global
    if "acc" in instruction:
        print("Accumulator instruction: {}".format(variable))
        accumulator = accumulator + eval(operator + variable)
        instruction_pointer += 1
        parse_instruction(instructions[instruction_pointer])

    # jump to a given instruction in the instructions list
    if "jmp" in instruction:
        print("Jumping to instruction: {}".format(variable))
        instruction_pointer = int(variable)
        parse_instruction(instructions[int(variable)])

    if "nop" in instruction:
        print("Do nothing yet")
        instruction_pointer += 1
        parse_instruction(instructions[instruction_pointer])

    


parse_instruction(instructions[0])
print("Result 1: {}".format(accumulator))
