defmodule AdventOfCode.Q23 do
  def get_raw_neighbours(grid, row, col) do
    case grid |> elem(row) |> elem(col) do
      ">" -> [{row, col + 1}]
      "v" -> [{row + 1, col}]
      "<" -> [{row, col - 1}]
      "^" -> [{row - 1, col}]
      _ -> [{row, col + 1}, {row + 1, col}, {row, col - 1}, {row - 1, col}]
    end
  end

  def is_valid_pos?(grid, row, col) do
    inside? =
      row >= 0 and row < tuple_size(grid) and col >= 0 and col < tuple_size(grid |> elem(row))

    inside? and not (grid |> elem(row) |> elem(col) == "#")
  end

  def get_neighbours(grid, row, col) do
    get_raw_neighbours(grid, row, col)
    |> Enum.filter(fn {cur_row, cur_col} -> is_valid_pos?(grid, cur_row, cur_col) end)
  end

  def get_longest_path(_grid, cur, target, visited) when cur == target do
    MapSet.size(visited)
  end

  def get_longest_path(grid, cur, target, visited) do
    if MapSet.member?(visited, cur) do
      0
    else
      new_visited = MapSet.put(visited, cur)
      {row, col} = cur
      neighbours = get_neighbours(grid, row, col)

      max_path =
        neighbours |> Enum.map(&get_longest_path(grid, &1, target, new_visited)) |> Enum.max()

      max_path
    end
  end

  def get_longest_path(grid, start, target) do
    get_longest_path(grid, start, target, MapSet.new())
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    grid =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.graphemes/1)
      |> Enum.map(&List.to_tuple/1)
      |> List.to_tuple()

    get_longest_path(grid, {0, 1}, {tuple_size(grid) - 1, tuple_size(grid |> elem(0)) - 2})
  end
end
