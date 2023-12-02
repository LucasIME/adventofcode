defmodule AdventOfCode.Q2Test do
  use ExUnit.Case
  doctest AdventOfCode.Q2

  test "Q2 Part 1 Example works" do
    file_stream = File.stream!("./test/q2/q2_example.txt")
    result = AdventOfCode.Q2.part1(file_stream)
    assert result == 8
  end

  test "Q2 Part 1 works" do
    file_stream = File.stream!("./test/q2/q2.txt")
    result = AdventOfCode.Q2.part1(file_stream)
    assert result == 2913
  end
end
