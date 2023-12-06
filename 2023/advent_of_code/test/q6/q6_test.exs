defmodule AdventOfCode.Q6Test do
  use ExUnit.Case
  doctest AdventOfCode.Q6

  test "Q6 Part 1 Example works" do
    file_stream = File.stream!("./test/q6/q6_example.txt")
    result = AdventOfCode.Q6.part1(file_stream)
    assert result == 288
  end

  test "Q6 Part 1 works" do
    file_stream = File.stream!("./test/q6/q6.txt")
    result = AdventOfCode.Q6.part1(file_stream)
    assert result == 608_902
  end
end
