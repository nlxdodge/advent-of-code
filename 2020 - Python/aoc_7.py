from helpers.helpers import read_input
input = read_input("aoc_7_test.txt", '\n')


class Tree:
    def __init__(self, name, amount=1):
        self.name = name
        self.amount = amount
        self.childs = list()

    def __repr__(self):
        """"Prints the bag with it children in it"""
        return "Bag: {} {} with {}".format(self.amount, self.name, self.childs)

    def add_child(self, child):
        """Adds a child to this tree object"""
        self.childs.append(child)

    def find_all_by_name(self, name, is_root_object = True):
        """"Recursively looks for a Tree object with the given name"""
        print("Looking for: {}".format(name))
        found = list()
        if self.name == name and not is_root_object:
            print("Found self: {}".format(self))
            found.append(self)
        for child in self.childs:
            found.extend(child.find_all_by_name(name))
            if child.name == name:
                print("Found child: {}".format(child))
                found.append(child)
        return found


# global list of bags
global_bags = list()


def find_bags_by_name(global_bags, name):
    found = list()
    for bag in global_bags:
        found.extend(bag.find_all_by_name(name, True))
    return found


def fill_childs(parent_tree, bag_rule):
    """This fills the childs in the original bag_rule"""
    child_rules = bag_rule.split(" contain ")[1].split(", ")
    child_rules[-1] = child_rules[-1].replace(".", "")
    for bag in child_rules:
        bag = bag.replace(" bags", "")
        bag = bag.replace(" bag", "")
        tree = Tree(bag[2:], bag[0:1])
        parent_tree.add_child(tree)

def print_global_bags(global_bags):
    print("All bags:")
    for tree in global_bags:
        print("    {}".format(tree))


for bag_rule in input:
    tree = Tree(bag_rule.split(" bags")[0])

    # bags containing nothing can't contain a gold bag, so they are to be omitted
    if bag_rule.find("contain no other bags") != -1:
        continue

    # filter and create childs for the given bag
    fill_childs(tree, bag_rule)

    # find all trees that should have this tree as sub tree and add it
    trees = find_bags_by_name(global_bags, tree.name)
    print("Found trees: {}".format(trees))
    for tree in trees:
        tree.add_child(tree)

    # all rules should be ommited to the root else we can't reapply the rule for each bag
    global_bags.append(tree)
print_global_bags(global_bags)



