defmodule AdventOfCode.Q10Test do
  use ExUnit.Case
  doctest AdventOfCode.Q10

  test "Q10 Part 1 Example works" do
    file_stream = File.stream!("./test/q10/q10_example.txt")
    result = AdventOfCode.Q10.part1(file_stream)
    assert result == 4
  end

  test "Q10 Part 1 Example 2 works" do
    file_stream = File.stream!("./test/q10/q10_example2.txt")
    result = AdventOfCode.Q10.part1(file_stream)
    assert result == 4
  end

  test "Q10 Part 1 Example 3 works" do
    file_stream = File.stream!("./test/q10/q10_example3.txt")
    result = AdventOfCode.Q10.part1(file_stream)
    assert result == 8
  end

  test "Q10 Part 1 works" do
    file_stream = File.stream!("./test/q10/q10.txt")
    result = AdventOfCode.Q10.part1(file_stream)
    assert result == 6820
  end

  test "Q10 Part 2 Example works" do
    file_stream = File.stream!("./test/q10/q10_example4.txt")
    result = AdventOfCode.Q10.part2(file_stream)
    assert result == 4
  end

  test "Q10 Part 2 Example 2 works" do
    file_stream = File.stream!("./test/q10/q10_example5.txt")
    result = AdventOfCode.Q10.part2(file_stream)
    assert result == 4
  end

  test "Q10 Part 2 Example 3 works" do
    file_stream = File.stream!("./test/q10/q10_example6.txt")
    result = AdventOfCode.Q10.part2(file_stream)
    assert result == 8
  end

  test "Q10 Part 2 Example 4 works" do
    file_stream = File.stream!("./test/q10/q10_example7.txt")
    result = AdventOfCode.Q10.part2(file_stream)
    assert result == 10
  end

  test "Q10 Part 2" do
    file_stream = File.stream!("./test/q10/q10.txt")
    result = AdventOfCode.Q10.part2(file_stream)
    assert result == 337
  end
end
