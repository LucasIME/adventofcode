defmodule AdventOfCode.Q20Test do
  use ExUnit.Case
  doctest AdventOfCode.Q20

  test "Q20 Part 1 Example works" do
    file_stream = File.stream!("./test/q20/q20_example.txt")
    result = AdventOfCode.Q20.part1(file_stream)
    assert result == 32_000_000
  end

  test "Q20 Part 1 Example 2 works" do
    file_stream = File.stream!("./test/q20/q20_example2.txt")
    result = AdventOfCode.Q20.part1(file_stream)
    assert result == 11_687_500
  end

  test "Q20 Part 1 works" do
    file_stream = File.stream!("./test/q20/q20.txt")
    result = AdventOfCode.Q20.part1(file_stream)
    assert result == 332_145
  end
end
