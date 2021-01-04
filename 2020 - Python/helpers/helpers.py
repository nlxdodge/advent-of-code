def read_input(file_name, char = "\n"):
    input = []
    with open(file_name, "r") as file:
        input = file.read().split(char)
        print("Lines read from input: ".format(len(input)))
        return input