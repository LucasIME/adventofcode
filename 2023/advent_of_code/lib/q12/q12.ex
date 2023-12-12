defmodule AdventOfCode.Q12 do
  def count(chars, numbers, count \\ 0)

  def count([], nums, count) do
    cond do
      Enum.empty?(nums) and count == 0 -> 1
      hd(nums) == count -> count([], tl(nums))
      true -> 0
    end
  end

  def count(["#" | _c_tail], [], _count) do
    0
  end

  def count(["?" | c_tail], [], count) do
    count(["." | c_tail], [], count)
  end

  def count(["." | c_tail], [], count) do
    if count == 0 do
      count(c_tail, [])
    else
      0
    end
  end

  def count(["#" | c_tail], [n | n_tail], count) do
    count(c_tail, [n | n_tail], count + 1)
  end

  def count(["?" | c_tail], [n | n_tail], count) do
    count(["#" | c_tail], [n | n_tail], count) + count(["." | c_tail], [n | n_tail], count)
  end

  def count(["." | c_tail], [n | n_tail], count) do
    cond do
      n == count -> count(c_tail, n_tail)
      count == 0 -> count(c_tail, [n | n_tail])
      true -> 0
    end
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    input
    |> Enum.map(&String.trim/1)
    |> Enum.map(&String.split(&1, " "))
    |> Enum.map(fn [record, raw_numbers] ->
      {String.graphemes(record),
       raw_numbers |> String.split(",") |> Enum.map(&String.to_integer/1)}
    end)
    |> Enum.map(fn {record, numbers} -> count(record, numbers) end)
    |> Enum.sum()
  end
end
