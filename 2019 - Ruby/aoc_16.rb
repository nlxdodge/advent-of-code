# frozen_string_literal: true

input = 59_768_092_839_927_758_565_191_298_625_215_106_371_890_118_051_426_250_855_924_764_194_411_528_004_718_709_886_402_903_435_569_627_982_485_301_921_649_240_820_059_827_161_024_631_612_290_005_106_304_724_846_680_415_690_183_371_469_037_418_126_383_450_370_741_078_684_974_598_662_642_956_794_012_825_271_487_329_243_583_117_537_873_565_332_166_744_128_845_006_806_878_717_955_946_534_158_837_370_451_935_919_790_469_815_143_341_599_820_016_469_368_684_893_122_766_857_261_426_799_636_559_525_003_877_090_579_845_725_676_481_276_977_781_270_627_558_901_433_501_565_337_409_716_858_949_203_430_181_103_278_194_428_546_385_063_911_239_478_804_717_744_977_998_841_434_061_688_000_383_456_176_494_210_691_861_957_243_370_245_170_223_862_304_663_932_874_454_624_234_226_361_642_678_259_020_094_801_774_825_694_423_060_700_312_504_286_475_305_674_864_442_250_709_029_812_379

base_pattern = [0, 1, 0, -1]

def base_pattern_modulo(base_pattern, index)
  base_pattern[index % 4]
end

def calculate_base_pattern(input, base_pattern, phase)
  phase_base_pattern = []
  (0..input.to_s.length - 1).each do |index|
    (0..phase - 1).each do
      phase_base_pattern << base_pattern_modulo(base_pattern, index)
    end
  end
  shift_array_left(
    phase_base_pattern,
    base_pattern_modulo(base_pattern, input.to_s.length)
  )
end

def shift_array_left(phase_base_pattern, to_add)
  phase_base_pattern.shift
  phase_base_pattern << to_add
end

def calculate_phase(input, base_pattern)
  new_input = ''
  input.to_s.chars.each.with_index do |_x, phase|
    total = 0
    input.to_s.chars.each.with_index do |number, number_index|
      phase_base_pattern = calculate_base_pattern(input, base_pattern, phase + 1)
      total += number.to_i * phase_base_pattern[number_index].to_i
    end
    new_input += total.to_s[-1]
    puts "#{phase}: Total this sub_phase: #{total}"
    # puts "Adding this char: #{total.to_s[-1]} making total: #{new_input}"
  end
  puts new_input
  abort
end

output = input

(1..100).each do |phase|
  output = calculate_phase(output, base_pattern)
  puts "Output for Phase #{phase} is: #{output}"
end

puts "Answer for part 1 is: #{output[0..7]}"
