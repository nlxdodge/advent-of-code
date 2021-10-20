def read_input(file_name, char = "\n"):
    read_lines = []
    with open(file_name, "r") as file:
        read_lines = file.read().split(char)
        print("Lines read from input file: {}".format(len(read_lines)))
        return read_lines