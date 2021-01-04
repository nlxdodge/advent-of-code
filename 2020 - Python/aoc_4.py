import re

from helpers.helpers import read_input

input = read_input("aoc_4_input.txt", '\n\n')


def valid_passport(key_values):
    to_check = ["byr", "iyr", "eyr", "hgt", "hcl", "ecl", "pid"]
    for check in to_check:
        if not check in key_values:
            return False
    return True


def between(variable, min, max):
    if (int(variable) < min or int(variable) > max):
        return False
    return True


def valid_height(input):
    if "cm" in input:
        height = int(input.replace("cm", ""))
        if height <= 193 and height >= 150:
            return True
    if "in" in input:
        height = int(input.replace("in", ""))
        if height <= 76 and height >= 59:
            return True
    return False


def valid_eye_color(input):
    return input in ["amb", "blu", "brn", "gry", "grn", "hzl", "oth"]


def valid_hair_color(input):
    return re.search("^#([a-f0-9]{6})$", input)


def valid_passport_number(input):
    return re.search("^([0-9]{9})$", input)


def extended_valid_passport(key_values):
    if not valid_passport(key_values):
        return False
    if not between(key_values['byr'], 1920, 2002):
        return False
    if not between(key_values['iyr'], 2010, 2020):
        return False
    if not between(key_values['eyr'], 2020, 2030):
        return False
    if not valid_height(key_values['hgt']):
        return False
    if not valid_hair_color(key_values['hcl']):
        return False
    if not valid_eye_color(key_values['ecl']):
        return False
    if not valid_passport_number(key_values['pid']):
        return False
    return True


valid_passports = 0
for passport in input:
    actual_passport = {}
    for line in passport.split("\n"):
        for key_value in line.split(" "):
            key_var = key_value.split(":")
            actual_passport[key_var[0]] = key_var[1]
    if valid_passport(actual_passport):
        valid_passports += 1

print("Result 1: {}".format(valid_passports))

valid_passports = 0
for passport in input:
    actual_passport = {}
    for line in passport.split("\n"):
        for key_value in line.split(" "):
            key_var = key_value.split(":")
            actual_passport[key_var[0]] = key_var[1]
    if extended_valid_passport(actual_passport):
        valid_passports += 1

print("Result 2: {}".format(valid_passports))
