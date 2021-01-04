# frozen_string_literal: true

def check_for_double(number)
  pointer = nil
  number.to_s.each_char do |char|
    return true if pointer.to_i == char.to_i

    pointer = char
  end
  false
end

def check_for_double_extended(number)
  has_single_double = false
  pointer = nil
  number.to_s.each_char.with_index do |char, index|
    has_single_double = true if char == number.to_s[index + 1]
    has_single_double = false if char == number.to_s[index + 2]
    has_single_double = false if char == number.to_s[index - 1]
    pointer = char
    return true if has_single_double
  end
  has_single_double
end

def check_for_low_to_high(number)
  pointer = 0
  number.to_s.each_char do |char|
    return false if pointer > char.to_i

    pointer = char.to_i
  end
  true
end

total = 0
(193_651..649_729).each do |number|
  total += 1 if check_for_double(number) && check_for_low_to_high(number)
end
puts "The correct password for part 1 is #{total}"

total = 0
(193_651..649_729).each do |number|
  if check_for_double_extended(number) && check_for_low_to_high(number)
    total += 1
  end
end
puts "The correct password for part 2 is #{total}"
