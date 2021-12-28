# frozen_string_literal: true

# pos: 1 -> 0 -> 12
# pos: 2 -> 0 -> 2

@op_codes = [
  1,
  0,
  0,
  3,
  1,
  1,
  2,
  3,
  1,
  3,
  4,
  3,
  1,
  5,
  0,
  3,
  2,
  13,
  1,
  19,
  1,
  19,
  10,
  23,
  1,
  23,
  6,
  27,
  1,
  6,
  27,
  31,
  1,
  13,
  31,
  35,
  1,
  13,
  35,
  39,
  1,
  39,
  13,
  43,
  2,
  43,
  9,
  47,
  2,
  6,
  47,
  51,
  1,
  51,
  9,
  55,
  1,
  55,
  9,
  59,
  1,
  59,
  6,
  63,
  1,
  9,
  63,
  67,
  2,
  67,
  10,
  71,
  2,
  71,
  13,
  75,
  1,
  10,
  75,
  79,
  2,
  10,
  79,
  83,
  1,
  83,
  6,
  87,
  2,
  87,
  10,
  91,
  1,
  91,
  6,
  95,
  1,
  95,
  13,
  99,
  1,
  99,
  13,
  103,
  2,
  103,
  9,
  107,
  2,
  107,
  10,
  111,
  1,
  5,
  111,
  115,
  2,
  115,
  9,
  119,
  1,
  5,
  119,
  123,
  1,
  123,
  9,
  127,
  1,
  127,
  2,
  131,
  1,
  5,
  131,
  0,
  99,
  2,
  0,
  14,
  0
]

@op_codes_default = @op_codes.clone

def reset_code
  @op_codes = @op_codes_default.clone
end

def get_code(index)
  @op_codes[@op_codes[index]]
end

def set_code(index, code)
  @op_codes[@op_codes[index]] = code
end

def do_calculation(index, times = false)
  nr1 = get_code(index + 1)
  nr2 = get_code(index + 2)
  if !times
    set_code(index + 3, nr1 + nr2)
  else
    set_code(index + 3, nr1 * nr2)
  end
end

def check_instruction_set
  (0..(@op_codes.length - 1)).step(4).each do |index|
    case @op_codes[index]
    when 1
      do_calculation(index)
    when 2
      do_calculation(index, true)
    when 99
      return @op_codes[0]
    else
      puts 'Command not found'
    end
  end
end

# part one
@op_codes[1] = 12
@op_codes[2] = 2
puts "Answer of part 1: #{check_instruction_set}"
reset_code

# part two
(0..99).each do |noun|
  (0..99).each do |verb|
    @op_codes[1] = noun
    @op_codes[2] = verb
    output = check_instruction_set
    if output == 19_690_720
      calculation = 100 * noun + verb
      puts "Answer of part 2: 100 * #{noun} + #{verb} = #{calculation}"
      abort
    end
    reset_code
  end
end
