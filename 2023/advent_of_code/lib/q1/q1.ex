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
    result
  end

  def parseInput(input) do
    input
    |> Enum.map(&String.trim/1)
    |> Enum.map(&String.graphemes/1)
    |> Enum.map(&toDigits/1)
    |> Enum.map(&Enum.reverse/1)
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    parseInput(input)
    |> Enum.map(fn list -> 10 * List.first(list) + List.last(list) end)
    |> Enum.sum()
  end
end
