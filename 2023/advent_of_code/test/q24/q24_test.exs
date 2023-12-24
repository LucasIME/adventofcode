defmodule AdventOfCode.Q24Test do
  use ExUnit.Case
  doctest AdventOfCode.Q24

  test "Q24 Part 1 Example works" do
    file_stream = File.stream!("./test/q24/q24_example.txt")
    result = AdventOfCode.Q24.part1(file_stream)
    assert result == 94
  end

  test "Q24 Part 1 works" do
    file_stream = File.stream!("./test/q24/q24.txt")
    result = AdventOfCode.Q24.part1(file_stream)
    assert result == 16939
  end
end
