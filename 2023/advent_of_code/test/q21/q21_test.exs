defmodule AdventOfCode.Q21Test do
  use ExUnit.Case
  doctest AdventOfCode.Q21

  test "Q21 Part 1 Example works" do
    file_stream = File.stream!("./test/q21/q21_example.txt")
    result = AdventOfCode.Q21.part1(file_stream, 6)
    assert result == 16
  end

  test "Q21 Part 1 works" do
    file_stream = File.stream!("./test/q21/q21.txt")
    result = AdventOfCode.Q21.part1(file_stream)
    assert result == 3724
  end

  test "Q21 Part 2 works" do
    file_stream = File.stream!("./test/q21/q21.txt")
    result = AdventOfCode.Q21.part2(file_stream)
    assert result == 620_348_631_910_321
  end
end
