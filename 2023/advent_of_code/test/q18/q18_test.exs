defmodule AdventOfCode.Q18Test do
  use ExUnit.Case
  doctest AdventOfCode.Q18

  test "Q18 Part 1 Example works" do
    file_stream = File.stream!("./test/q18/q18_example.txt")
    result = AdventOfCode.Q18.part1(file_stream)
    assert result == 62
  end

  test "Q18 Part 1 works" do
    file_stream = File.stream!("./test/q18/q18.txt")
    result = AdventOfCode.Q18.part1(file_stream)
    assert result == 26857
  end

  test "Q18 Part 2 Example works" do
    file_stream = File.stream!("./test/q18/q18_example.txt")
    result = AdventOfCode.Q18.part2(file_stream)
    assert result == 952408144115
  end

  test "Q18 Part 2 works" do
    file_stream = File.stream!("./test/q18/q18.txt")
    result = AdventOfCode.Q18.part2(file_stream)
    assert result == 129373230496292
  end
end
