defmodule AdventOfCode.Q19Test do
  use ExUnit.Case
  doctest AdventOfCode.Q19

  test "Q19 Part 1 Example works" do
    file_stream = File.stream!("./test/q19/q19_example.txt")
    result = AdventOfCode.Q19.part1(file_stream)
    assert result == 19114
  end

  test "Q19 Part 1 works" do
    file_stream = File.stream!("./test/q19/q19.txt")
    result = AdventOfCode.Q19.part1(file_stream)
    assert result == 26857
  end
end
