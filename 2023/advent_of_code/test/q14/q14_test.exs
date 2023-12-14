defmodule AdventOfCode.Q14Test do
  use ExUnit.Case
  doctest AdventOfCode.Q14

  test "Q14 Part 1 Example works" do
    file_stream = File.stream!("./test/q14/q14_example.txt")
    result = AdventOfCode.Q14.part1(file_stream)
    assert result == 136
  end

  test "Q14 Part 1 works" do
    file_stream = File.stream!("./test/q14/q14.txt")
    result = AdventOfCode.Q14.part1(file_stream)
    assert result == 110_779
  end

  test "Q14 Part 2 Example works" do
    file_stream = File.stream!("./test/q14/q14_example.txt")
    result = AdventOfCode.Q14.part2(file_stream)
    assert result == 64
  end

  test "Q14 Part 2 works" do
    file_stream = File.stream!("./test/q14/q14.txt")
    result = AdventOfCode.Q14.part2(file_stream)
    assert result == 86069
  end
end
