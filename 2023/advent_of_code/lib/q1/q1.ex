defmodule AdventOfCode.Q1 do
  def toDigits(cList) do
    toDigits(cList, [])
  end

  def toDigits([head | tail], result) do
    case Integer.parse(head) do
      {num, _} ->
        toDigits(tail, [num | result])

      :error ->
        toDigits(tail, result)
    end
  end

  def toDigits([], result) do
    Enum.reverse(result)
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    input
    |> Enum.map(&String.trim/1)
    |> Enum.map(&String.graphemes/1)
    |> Enum.map(&toDigits/1)
    |> Enum.map(fn list -> 10 * List.first(list) + List.last(list) end)
    |> Enum.sum()
  end

  def toDigitsWithWords(s, result \\ []) do
    case s do
      ["1" | tail] -> toDigitsWithWords(tail, [1 | result])
      ["2" | tail] -> toDigitsWithWords(tail, [2 | result])
      ["3" | tail] -> toDigitsWithWords(tail, [3 | result])
      ["4" | tail] -> toDigitsWithWords(tail, [4 | result])
      ["5" | tail] -> toDigitsWithWords(tail, [5 | result])
      ["6" | tail] -> toDigitsWithWords(tail, [6 | result])
      ["7" | tail] -> toDigitsWithWords(tail, [7 | result])
      ["8" | tail] -> toDigitsWithWords(tail, [8 | result])
      ["9" | tail] -> toDigitsWithWords(tail, [9 | result])
      ["o", "n", "e" | _tail] -> toDigitsWithWords(List.delete_at(s, 0), [1 | result])
      ["t", "w", "o" | _tail] -> toDigitsWithWords(List.delete_at(s, 0), [2 | result])
      ["t", "h", "r", "e", "e" | _tail] -> toDigitsWithWords(List.delete_at(s, 0), [3 | result])
      ["f", "o", "u", "r" | _tail] -> toDigitsWithWords(List.delete_at(s, 0), [4 | result])
      ["f", "i", "v", "e" | _tail] -> toDigitsWithWords(List.delete_at(s, 0), [5 | result])
      ["s", "i", "x" | _tail] -> toDigitsWithWords(List.delete_at(s, 0), [6 | result])
      ["s", "e", "v", "e", "n" | _tail] -> toDigitsWithWords(List.delete_at(s, 0), [7 | result])
      ["e", "i", "g", "h", "t" | _tail] -> toDigitsWithWords(List.delete_at(s, 0), [8 | result])
      ["n", "i", "n", "e" | _tail] -> toDigitsWithWords(List.delete_at(s, 0), [9 | result])
      [] -> Enum.reverse(result)
      [_ | tail] -> toDigitsWithWords(tail, result)
    end
  end

  def part2(input \\ IO.stream(:stdio, :line)) do
    input
    |> Enum.map(&String.trim/1)
    |> Enum.map(&String.graphemes/1)
    |> Enum.map(&toDigitsWithWords/1)
    |> Enum.map(fn list -> 10 * List.first(list) + List.last(list) end)
    |> Enum.sum()
  end
end
