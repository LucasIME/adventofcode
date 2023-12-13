defmodule AdventOfCode.Q13Test do
  use ExUnit.Case
  doctest AdventOfCode.Q13

  test "Q13 Part 1 Example works" do
    file_stream = File.stream!("./test/q13/q13_example.txt")
    result = AdventOfCode.Q13.part1(file_stream)
    assert result == 405
  end

  test "Q13 Part 1 works" do
    file_stream = File.stream!("./test/q13/q13.txt")
    result = AdventOfCode.Q13.part1(file_stream)
    assert result == 39939
  end

  test "Q13 Part 2 Example works" do
    file_stream = File.stream!("./test/q13/q13_example.txt")
    result = AdventOfCode.Q13.part2(file_stream)
    assert result == 400
  end

  test "Q13 Part 2 works" do
    file_stream = File.stream!("./test/q13/q13.txt")
    result = AdventOfCode.Q13.part2(file_stream)
    assert result == 32069
  end
end
