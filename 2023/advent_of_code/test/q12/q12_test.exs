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
end
