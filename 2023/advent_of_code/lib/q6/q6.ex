defmodule AdventOfCode.Q6 do
  def get_winning_count(0, _total_time, _distance) do
    0
  end

  def get_winning_count(hold_time, total_time, distance) do
    traveled = (total_time - hold_time) * hold_time

    if traveled > distance do
      1 + get_winning_count(hold_time - 1, total_time, distance)
    else
      get_winning_count(hold_time - 1, total_time, distance)
    end
  end

  def get_winning_count({time, distance}) do
    get_winning_count(time, time, distance)
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    [time, distance] =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.split(&1, ":"))
      |> Enum.flat_map(&tl/1)
      |> Enum.map(&String.split(&1, " ", trim: true))
      |> Enum.map(fn list -> Enum.map(list, &String.to_integer/1) end)

    time_and_distance_list = Enum.zip(time, distance)

    time_and_distance_list
    |> Enum.map(&get_winning_count/1)
    |> Enum.reduce(&*/2)
  end
end
