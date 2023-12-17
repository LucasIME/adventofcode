defmodule AdventOfCode.Q17Test do
  use ExUnit.Case
  doctest AdventOfCode.Q17

  test "Q17 Part 1 Example works" do
    file_stream = File.stream!("./test/q17/q17_example.txt")
    result = AdventOfCode.Q17.part1(file_stream)
    assert result == 102
  end

  # Taking roughly 5 min on my M1 Mac
  @tag timeout: 600_000
  test "Q17 Part 1 works" do
    file_stream = File.stream!("./test/q17/q17.txt")
    result = AdventOfCode.Q17.part1(file_stream)
    assert result == 1256
  end

  test "Q17 Part 2 Example works" do
    file_stream = File.stream!("./test/q17/q17_example.txt")
    result = AdventOfCode.Q17.part2(file_stream)
    assert result == 94
  end

  test "Q17 Part 2 Example 2 works" do
    file_stream = File.stream!("./test/q17/q17_example.txt")
    result = AdventOfCode.Q17.part2(file_stream)
    assert result == 71
  end

  # Taking roughly 5 min on my M1 Mac
  @tag timeout: 600_000
  test "Q17 Part 2 works" do
    file_stream = File.stream!("./test/q17/q17.txt")
    result = AdventOfCode.Q17.part2(file_stream)
    assert result == 1382
  end
end
