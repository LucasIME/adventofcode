defmodule AdventOfCode.Q9Test do
  use ExUnit.Case
  doctest AdventOfCode.Q9

  test "Q9 Part 1 Example works" do
    file_stream = File.stream!("./test/q9/q9_example.txt")
    result = AdventOfCode.Q9.part1(file_stream)
    assert result == 114
  end

  test "Q9 Part 1 works" do
    file_stream = File.stream!("./test/q9/q9.txt")
    result = AdventOfCode.Q9.part1(file_stream)
    assert result == 1901217887
  end
end
