defmodule AdventOfCode.Q9 do
  def all_zero([]) do
    true
  end

  def all_zero([head | tail]) do
    head == 0 and all_zero(tail)
  end

  def predict_next(num_list) do
    shifted_list = Enum.drop(num_list, 1)
    pair_list = Enum.zip(num_list, shifted_list)
    diff_list = Enum.map(pair_list, fn {val, next} -> next - val end)

    last_element = num_list |> Enum.reverse() |> hd

    if all_zero(diff_list) do
      last_element
    else
      last_element + predict_next(diff_list)
    end
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    input
    |> Enum.map(&String.trim/1)
    |> Enum.map(&String.split(&1, " "))
    |> Enum.map(fn raw_list -> Enum.map(raw_list, &String.to_integer/1) end)
    |> Enum.map(&predict_next/1)
    |> Enum.sum()
  end
end
