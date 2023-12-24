defmodule AdventOfCode.Q24 do
  def to_pos_and_vel(line) do
    [raw_pos, raw_vel] =
      line
      |> String.split(" @ ")

    pos =
      raw_pos
      |> String.split(",")
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.to_integer/1)
      |> List.to_tuple()

    vel =
      raw_vel
      |> String.split(",")
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.to_integer/1)
      |> List.to_tuple()

    {pos, vel}
  end

  def crosses_within_area(p1, p2, low, high) do
    {{x1, y1, _z1}, {vx1, vy1, _vz1}} = p1
    {{x2, y2, _z2}, {vx2, vy2, _vz2}} = p2

    if vx1 == 0 or vy1 * vx2 - vx1 * vy2 == 0 do
      false
    else
      t2 = (vx1 * (y2 - y1) + vy1 * x1 - vy1 * x2) / (vy1 * vx2 - vx1 * vy2)
      t1 = (vx2 * t2 + x2 - x1) / vx1

      new_x = x1 + vx1 * t1
      new_y = y1 + vy1 * t1

      new_x >= low and new_x <= high and (new_y >= low and new_y <= high) and t1 >= 0 and
        t2 >= 0
    end
  end

  def get_crossings(pos_and_velocities, low, high) do
    all_pairs = for p1 <- pos_and_velocities, p2 <- pos_and_velocities, p1 != p2, do: {p1, p2}

    double_pairs =
      all_pairs
      |> Enum.filter(fn {p1, p2} -> crosses_within_area(p1, p2, low, high) end)
      |> Enum.count()

    # we count all pairs twice
    double_pairs |> div(2)
  end

  def part1(
        input \\ IO.stream(:stdio, :line),
        low \\ 200_000_000_000_000,
        high \\ 400_000_000_000_000
      ) do
    postitions_and_velocities =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&to_pos_and_vel/1)

    get_crossings(postitions_and_velocities, low, high)
  end
end
