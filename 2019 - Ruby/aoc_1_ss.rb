# frozen_string_literal: true

variable = [
  50_951,
  69_212,
  119_076,
  124_303,
  95_335,
  65_069,
  109_778,
  113_786,
  124_821,
  103_423,
  128_775,
  111_918,
  138_158,
  141_455,
  92_800,
  50_908,
  107_279,
  77_352,
  129_442,
  60_097,
  84_670,
  143_682,
  104_335,
  105_729,
  87_948,
  59_542,
  81_481,
  147_508,
  62_687,
  64_212,
  66_794,
  99_506,
  137_804,
  135_065,
  135_748,
  110_879,
  114_412,
  120_414,
  72_723,
  50_412,
  124_079,
  57_885,
  95_601,
  74_974,
  69_000,
  66_567,
  118_274,
  136_432,
  110_395,
  88_893,
  124_962,
  74_296,
  106_148,
  59_764,
  123_059,
  106_473,
  50_725,
  116_256,
  80_314,
  60_965,
  134_002,
  53_389,
  82_528,
  144_323,
  87_791,
  128_288,
  109_929,
  64_373,
  114_510,
  116_897,
  84_697,
  75_358,
  109_246,
  110_681,
  94_543,
  92_590,
  69_865,
  83_912,
  124_275,
  94_276,
  98_210,
  69_752,
  100_315,
  142_879,
  94_783,
  111_939,
  64_170,
  83_629,
  138_743,
  141_238,
  77_068,
  119_299,
  81_095,
  96_515,
  126_853,
  87_563,
  101_299,
  130_240,
  62_693,
  139_018
]

total_fuel = 0

def calculate(input)
  (input / 3).round(half: :down) - 2
end

def calculate_advanced(input)
  product_total = 0
  product = input
  while product >= 0
    product = calculate(product)
    product_total += product if product.positive?
  end
  product_total
end

variable.each do |var|
  total_fuel += calculate(var)
end

puts 'Answer for part 1:'
puts total_fuel
puts ''

total_fuel = 0

variable.each do |var|
  total_fuel += calculate_advanced(var)
end

puts 'Answer for part 2:'
puts total_fuel
