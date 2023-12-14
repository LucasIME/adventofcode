defmodule AdventOfCode.Q14 do
  def transpose(grid) do
    grid
    |> Enum.zip()
    |> Enum.map(&Tuple.to_list/1)
  end

  def tilt_line_left([], rocks, cur_empties) do
    rocks ++ cur_empties
  end

  def tilt_line_left([head | tail], cur_rocks, cur_empties) do
    case head do
      "." ->
        tilt_line_left(tail, cur_rocks, ["." | cur_empties])

      "#" ->
        cur_rocks ++ cur_empties ++ ["#"] ++ tilt_line_left(tail, [], [])

      "O" ->
        tilt_line_left(tail, ["O" | cur_rocks], cur_empties)
    end
  end

  def tilt_line_left(line) do
    tilt_line_left(line, [], [])
  end

  def tilt_line_right([], rocks, cur_empties) do
    cur_empties ++ rocks
  end

  def tilt_line_right([head | tail], cur_rocks, cur_empties) do
    case head do
      "." ->
        tilt_line_right(tail, cur_rocks, ["." | cur_empties])

      "#" ->
        cur_empties ++ cur_rocks ++ ["#"] ++ tilt_line_right(tail, [], [])

      "O" ->
        tilt_line_right(tail, ["O" | cur_rocks], cur_empties)
    end
  end

  def tilt_line_right(line) do
    tilt_line_right(line, [], [])
  end


  def tilt_north(grid) do
    grid = transpose(grid)

    grid
    |> Enum.map(&tilt_line_left/1)
    |> transpose()
  end

  def tilt_south(grid) do
    grid = transpose(grid)

    grid
    |> Enum.map(&tilt_line_right/1)
    |> transpose()
  end

  def tilt_west(grid) do
    grid |> Enum.map(&tilt_line_left/1)
  end

  def tilt_east(grid) do
    grid |> Enum.map(&tilt_line_right/1)
  end

  def points_c(c, row_num, total_rows) do
    if c == "O" do
      total_rows - row_num
    else
      0
    end
  end

  def points_row(row, row_num, total_rows) do
    row |> Enum.map(fn c -> points_c(c, row_num, total_rows) end) |> Enum.sum()
  end

  def points(grid) do
    total_rows = length(grid)

    grid
    |> Enum.with_index()
    |> Enum.map(fn {row, index} -> points_row(row, index, total_rows) end)
    |> Enum.sum()
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    grid =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.graphemes/1)

    grid |> tilt_north() |> points()
  end

  def spin_cycle(grid) do
    grid |> tilt_north() |> tilt_west() |> tilt_south() |> tilt_east()
  end

  def spin_many(grid, 0) do
    grid
  end

  def spin_many(grid, n) do
    spinned = spin_cycle(grid)
    spin_many(spinned, n-1)
  end

  def points_after_spinning(n, repeating) do
    index = rem(n, tuple_size(repeating))
    elem(repeating, index + 1)
  end

  def spin_until_seen(grid, count, seen) do
    if MapSet.member?(seen, grid) do
      {grid, count}
    else
      seen = MapSet.put(seen, grid)
      spin_until_seen(spin_cycle(grid), count + 1, seen)
    end
  end

  def spin_until_seen(grid) do
    spin_until_seen(grid, 0, MapSet.new())
  end

  def part2(input \\ IO.stream(:stdio, :line)) do
    grid =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.graphemes/1)

      {first_repeated_pattern, non_repeating_prefix} = spin_until_seen(grid)
      {second_repeated_pattern, repeating_frequency} = spin_until_seen(first_repeated_pattern)

      target_index = 1000000000
      required_spins = target_index - non_repeating_prefix |> rem(repeating_frequency)

      spin_many(second_repeated_pattern, required_spins) |> points()
  end
end
