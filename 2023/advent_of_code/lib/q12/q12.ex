defmodule AdventOfCode.Q12 do
  use Memoize

  defmemo(count(chars, numbers, count \\ 0))

  defmemo count([], nums, count) do
    cond do
      Enum.empty?(nums) and count == 0 -> 1
      hd(nums) == count -> count([], tl(nums))
      true -> 0
    end
  end

  defmemo count(["#" | _c_tail], [], _count) do
    0
  end

  defmemo count(["?" | c_tail], [], count) do
    count(["." | c_tail], [], count)
  end

  defmemo count(["." | c_tail], [], count) do
    if count == 0 do
      count(c_tail, [])
    else
      0
    end
  end

  defmemo count(["#" | c_tail], [n | n_tail], count) do
    count(c_tail, [n | n_tail], count + 1)
  end

  defmemo count(["?" | c_tail], [n | n_tail], count) do
    count(["#" | c_tail], [n | n_tail], count) + count(["." | c_tail], [n | n_tail], count)
  end

  defmemo count(["." | c_tail], [n | n_tail], count) do
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

  def repeat_list(_list, n, _middle) when n <= 0 do
    []
  end

  def repeat_list(list, n, middle) do
    repeated = repeat_list(list, n - 1, middle)

    if Enum.empty?(repeated) do
      list ++ repeated
    else
      list ++ middle ++ repeated
    end
  end

  def part2(input \\ IO.stream(:stdio, :line)) do
    input
    |> Enum.map(&String.trim/1)
    |> Enum.map(&String.split(&1, " "))
    |> Enum.map(fn [record, raw_numbers] ->
      {String.graphemes(record),
       raw_numbers |> String.split(",") |> Enum.map(&String.to_integer/1)}
    end)
    |> Enum.map(fn {record, numbers} ->
      {repeat_list(record, 5, ["?"]), repeat_list(numbers, 5, [])}
    end)
    |> Enum.map(fn {record, numbers} -> count(record, numbers) end)
    |> Enum.sum()
  end
end
