defmodule AdventOfCode.Q16 do
  def is_valid?(matrix, row, col) do
    row >= 0 and row < tuple_size(matrix) and col >= 0 and col < tuple_size(elem(matrix, row))
  end

  def get_next(_matrix, row, col, dir) do
    case dir do
      :right -> {row, col + 1}
      :left -> {row, col - 1}
      :up -> {row - 1, col}
      :down -> {row + 1, col}
    end
  end

  def get_new_dirs(char, dir) do
    case dir do
      :right ->
        case char do
          "\\" -> [:down]
          "/" -> [:up]
          "|" -> [:up, :down]
          _ -> [:right]
        end

      :left ->
        case char do
          "\\" -> [:up]
          "/" -> [:down]
          "|" -> [:up, :down]
          _ -> [:left]
        end

      :up ->
        case char do
          "\\" -> [:left]
          "/" -> [:right]
          "-" -> [:left, :right]
          _ -> [:up]
        end

      :down ->
        case char do
          "\\" -> [:right]
          "/" -> [:left]
          "-" -> [:left, :right]
          _ -> [:down]
        end
    end
  end

  def energized(matrix, row, col, dir, visited) do
    if not MapSet.member?(visited, {row, col, dir}) do
      visited = MapSet.put(visited, {row, col, dir})

      {next_row, next_col} = get_next(matrix, row, col, dir)

      if is_valid?(matrix, next_row, next_col) do
        char = matrix |> elem(next_row) |> elem(next_col)
        new_dirs = get_new_dirs(char, dir)

        Enum.reduce(new_dirs, visited, fn new_dir, acc_visited ->
          energized(matrix, next_row, next_col, new_dir, acc_visited)
        end)
      else
        visited
      end
    else
      visited
    end
  end

  def energized(matrix) do
    energized(matrix, 0, -1, :right, MapSet.new())
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    matrix =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.graphemes/1)
      |> Enum.map(&List.to_tuple/1)
      |> List.to_tuple()

    all_places = energized(matrix)

    seen =
      all_places
      |> Enum.map(fn {row, col, _dir} -> {row, col} end)
      |> MapSet.new()
      |> MapSet.size()

    # have to disconsider the (0, -1) where we start
    seen - 1
  end
end
