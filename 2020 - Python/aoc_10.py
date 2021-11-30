from helpers.helpers import read_input
adapters = read_input("input_files/aoc_10_input.txt", '\n')

adapters = [int(x) for x in adapters]
adapters.sort()


def get_device_jolts(adapters):
    return adapters[-1] + 3


adapter_counter = [1, 0, 1]

for index in range(0, len(adapters)):
    for lookahead in range(1, 4):
        if len(adapters) > (index + 1) and adapters[index] + lookahead == adapters[index + 1]:
            adapter_counter[lookahead - 1] = adapter_counter[lookahead - 1] + 1
            break

print("Result 2: {}".format(adapter_counter[0] * adapter_counter[2]))
