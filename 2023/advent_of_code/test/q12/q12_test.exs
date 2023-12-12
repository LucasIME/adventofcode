defmodule AdventOfCode.Q12Test do
  use ExUnit.Case
  doctest AdventOfCode.Q12

  test "Q12 Part 1 Example works" do
    file_stream = File.stream!("./test/q12/q12_example.txt")
    result = AdventOfCode.Q12.part1(file_stream)
    assert result == 21
  end

  test "Q12 Part 1 works" do
    file_stream = File.stream!("./test/q12/q12.txt")
    result = AdventOfCode.Q12.part1(file_stream)
    assert result == 8075
  end

  test "Q12 Part 2 Example works" do
    file_stream = File.stream!("./test/q12/q12_example.txt")
    result = AdventOfCode.Q12.part2(file_stream)
    assert result == 525_152
  end

  @tag timeout: 120_000 # Taking roughly 70 seconds on my M1 Mac
  test "Q12 Part 2 works" do
    file_stream = File.stream!("./test/q12/q12.txt")
    result = AdventOfCode.Q12.part2(file_stream)
    assert result == 4_232_520_187_524
  end
end
