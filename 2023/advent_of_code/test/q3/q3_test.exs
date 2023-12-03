defmodule AdventOfCode.Q3Test do
  use ExUnit.Case
  doctest AdventOfCode.Q3

  test "Q3 Part 1 Example works" do
    file_stream = File.stream!("./test/q3/q3_example.txt")
    result = AdventOfCode.Q3.part1(file_stream)
    assert result == 4361
  end

  test "Q3 Part 1 works" do
    file_stream = File.stream!("./test/q3/q3.txt")
    result = AdventOfCode.Q3.part1(file_stream)
    assert result == 535_078
  end
end
