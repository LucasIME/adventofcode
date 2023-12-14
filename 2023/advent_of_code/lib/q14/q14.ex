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

  def tilt(grid) do
    grid = transpose(grid)

    grid
    |> Enum.map(&tilt_line_left/1)
    |> transpose()
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

    grid |> tilt() |> points()
  end
end
