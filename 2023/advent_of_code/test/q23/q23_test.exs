defmodule AdventOfCode.Q23Test do
  use ExUnit.Case
  doctest AdventOfCode.Q23

  test "Q23 Part 1 Example works" do
    file_stream = File.stream!("./test/q23/q23_example.txt")
    result = AdventOfCode.Q23.part1(file_stream)
    assert result == 94
  end

  test "Q23 Part 1 works" do
    file_stream = File.stream!("./test/q23/q23.txt")
    result = AdventOfCode.Q23.part1(file_stream)
    assert result == 1998
  end

  test "Q23 Part 2 Example works" do
    file_stream = File.stream!("./test/q23/q23_example.txt")
    result = AdventOfCode.Q23.part2(file_stream)
    assert result == 154
  end

  test "Q23 Part 2 works" do
    file_stream = File.stream!("./test/q23/q23.txt")
    result = AdventOfCode.Q23.part2(file_stream)
    assert result == 6434
  end
end
