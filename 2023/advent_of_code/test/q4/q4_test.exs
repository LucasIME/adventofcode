defmodule AdventOfCode.Q4Test do
  use ExUnit.Case
  doctest AdventOfCode.Q4

  test "Q4 Part 1 Example works" do
    file_stream = File.stream!("./test/q4/q4_example.txt")
    result = AdventOfCode.Q4.part1(file_stream)
    assert result == 13
  end

  test "Q4 Part 1 works" do
    file_stream = File.stream!("./test/q4/q4.txt")
    result = AdventOfCode.Q4.part1(file_stream)
    assert result == 33950
  end
end
