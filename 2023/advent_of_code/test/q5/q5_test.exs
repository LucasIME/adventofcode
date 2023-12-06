defmodule AdventOfCode.Q5Test do
  use ExUnit.Case
  doctest AdventOfCode.Q5

  test "Q5 Part 1 Example works" do
    file_stream = File.stream!("./test/q5/q5_example.txt")
    result = AdventOfCode.Q5.part1(file_stream)
    assert result == 35
  end

  test "Q5 Part 1 works" do
    file_stream = File.stream!("./test/q5/q5.txt")
    result = AdventOfCode.Q5.part1(file_stream)
    assert result == 484_023_871
  end

  test "Q5 Part 2 Example works" do
    file_stream = File.stream!("./test/q5/q5_example.txt")
    result = AdventOfCode.Q5.part2(file_stream)
    assert result == 46
  end

  test "Q5 Part 2 works" do
    file_stream = File.stream!("./test/q5/q5.txt")
    result = AdventOfCode.Q5.part2(file_stream)
    assert result == 46_294_175
  end
end
