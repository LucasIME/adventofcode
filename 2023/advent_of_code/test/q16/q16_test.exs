defmodule AdventOfCode.Q16Test do
  use ExUnit.Case
  doctest AdventOfCode.Q16

  test "Q16 Part 1 Example works" do
    file_stream = File.stream!("./test/q16/q16_example.txt")
    result = AdventOfCode.Q16.part1(file_stream)
    assert result == 46
  end

  test "Q16 Part 1 works" do
    file_stream = File.stream!("./test/q16/q16.txt")
    result = AdventOfCode.Q16.part1(file_stream)
    assert result == 7111
  end
end
