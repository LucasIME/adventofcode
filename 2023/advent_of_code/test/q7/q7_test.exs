defmodule AdventOfCode.Q7Test do
  use ExUnit.Case
  doctest AdventOfCode.Q7

  test "Q7 Part 1 Example works" do
    file_stream = File.stream!("./test/q7/q7_example.txt")
    result = AdventOfCode.Q7.part1(file_stream)
    assert result == 6440
  end

  test "Q7 Part 1 works" do
    file_stream = File.stream!("./test/q7/q7.txt")
    result = AdventOfCode.Q7.part1(file_stream)
    assert result == 241_344_943
  end

  test "Q7 Part 2 Example works" do
    file_stream = File.stream!("./test/q7/q7_example.txt")
    result = AdventOfCode.Q7.part2(file_stream)
    assert result == 5905
  end

  test "Q7 Part 2 works" do
    file_stream = File.stream!("./test/q7/q7.txt")
    result = AdventOfCode.Q7.part2(file_stream)
    assert result == 243_101_568
  end
end
