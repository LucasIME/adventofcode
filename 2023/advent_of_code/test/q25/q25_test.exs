defmodule AdventOfCode.Q25Test do
  use ExUnit.Case
  doctest AdventOfCode.Q25

  test "Q25 Part 1 Example works" do
    file_stream = File.stream!("./test/q25/q25_example.txt")
    result = AdventOfCode.Q25.part1(file_stream)
    assert result == 54
  end

  test "Q25 Part 1 works" do
    file_stream = File.stream!("./test/q25/q25.txt")
    result = AdventOfCode.Q25.part1(file_stream)
    assert result == 567_606
  end
end
