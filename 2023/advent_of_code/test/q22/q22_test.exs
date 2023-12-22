defmodule AdventOfCode.Q22Test do
  use ExUnit.Case
  doctest AdventOfCode.Q22

  test "Q22 Part 1 Example works" do
    file_stream = File.stream!("./test/q22/q22_example.txt")
    result = AdventOfCode.Q22.part1(file_stream)
    assert result == 5
  end

  test "Q22 Part 1 works" do
    file_stream = File.stream!("./test/q22/q22.txt")
    result = AdventOfCode.Q22.part1(file_stream)
    assert result == 428
  end

  test "Q22 Part 2 Example works" do
    file_stream = File.stream!("./test/q22/q22_example.txt")
    result = AdventOfCode.Q22.part2(file_stream)
    assert result == 7
  end

  test "Q22 Part 2 works" do
    file_stream = File.stream!("./test/q22/q22.txt")
    result = AdventOfCode.Q22.part2(file_stream)
    assert result == 35654
  end
end
