defmodule AdventOfCode.Q11Test do
  use ExUnit.Case
  doctest AdventOfCode.Q11

  test "Q11 Part 1 Example works" do
    file_stream = File.stream!("./test/q11/q11_example.txt")
    result = AdventOfCode.Q11.part1(file_stream)
    assert result == 374
  end

  test "Q11 Part 1 works" do
    file_stream = File.stream!("./test/q11/q11.txt")
    result = AdventOfCode.Q11.part1(file_stream)
    assert result == 9_734_203
  end

  test "Q11 Part 2 Example works" do
    file_stream = File.stream!("./test/q11/q11_example.txt")
    result = AdventOfCode.Q11.part2(file_stream, 10)
    assert result == 1030
  end

  test "Q11 Part 2 Example 2 works" do
    file_stream = File.stream!("./test/q11/q11_example.txt")
    result = AdventOfCode.Q11.part2(file_stream, 100)
    assert result == 8410
  end

  test "Q11 Part 2 works" do
    file_stream = File.stream!("./test/q11/q11.txt")
    result = AdventOfCode.Q11.part2(file_stream)
    assert result == 568_914_596_391
  end
end
