defmodule AdventOfCode.Q6 do
  def get_winning_count({time, distance}) do
    delta_sqrt = :math.sqrt(time * time - 4 * (-1) * (-1 * distance))

    x1 = (( (-1 * time) - delta_sqrt ) / (2 * -1)) |> :math.ceil() |> trunc()
    x2 = (( (-1 * time) + delta_sqrt ) / (2 * -1)) |> :math.floor() |> trunc()

    x2 - x1 + 1 |> abs()
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

  def part2(input \\ IO.stream(:stdio, :line)) do
    [time, distance] =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.split(&1, ":"))
      |> Enum.flat_map(&tl/1)
      |> Enum.map(&String.replace(&1, " ", ""))
      |> Enum.map(&String.to_integer/1)

    get_winning_count({time, distance})
  end
end
