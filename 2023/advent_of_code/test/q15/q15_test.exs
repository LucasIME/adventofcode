defmodule AdventOfCode.Q15Test do
  use ExUnit.Case
  doctest AdventOfCode.Q15

  test "Q15 hash works" do
    results = AdventOfCode.Q15.hash("HASH" |> String.graphemes())
    assert result = 52
  end

  test "Q15 Part 1 Example works" do
    file_stream = File.stream!("./test/q15/q15_example.txt")
    result = AdventOfCode.Q15.part1(file_stream)
    assert result == 1320
  end

  test "Q15 Part 1 works" do
    file_stream = File.stream!("./test/q15/q15.txt")
    result = AdventOfCode.Q15.part1(file_stream)
    assert result == 509152
  end
end
