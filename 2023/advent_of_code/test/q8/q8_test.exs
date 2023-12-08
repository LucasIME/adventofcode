defmodule AdventOfCode.Q8Test do
  use ExUnit.Case
  doctest AdventOfCode.Q8

  test "Q8 Part 1 Example works" do
    file_stream = File.stream!("./test/q8/q8_example.txt")
    result = AdventOfCode.Q8.part1(file_stream)
    assert result == 2
  end

  test "Q8 Part 1 Example2 works" do
    file_stream = File.stream!("./test/q8/q8_example2.txt")
    result = AdventOfCode.Q8.part1(file_stream)
    assert result == 6
  end

  test "Q8 Part 1 works" do
    file_stream = File.stream!("./test/q8/q8.txt")
    result = AdventOfCode.Q8.part1(file_stream)
    assert result == 21883
  end
end
