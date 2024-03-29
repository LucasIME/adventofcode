defmodule AdventOfCode.Q1Test do
  use ExUnit.Case
  doctest AdventOfCode.Q1

  test "Q1 Part 1 Example works" do
    file_stream = File.stream!("./test/q1/q1_example.txt")
    result = AdventOfCode.Q1.part1(file_stream)
    assert result == 142
  end

  test "Q1 Part 1 works" do
    file_stream = File.stream!("./test/q1/q1.txt")
    result = AdventOfCode.Q1.part1(file_stream)
    assert result == 54634
  end

  test "Q1 Part 2 Example works" do
    file_stream = File.stream!("./test/q1/q1_example2.txt")
    result = AdventOfCode.Q1.part2(file_stream)
    assert result == 281
  end

  test "Q1 Part 2 works" do
    file_stream = File.stream!("./test/q1/q1.txt")
    result = AdventOfCode.Q1.part2(file_stream)
    assert result == 53855
  end
end
