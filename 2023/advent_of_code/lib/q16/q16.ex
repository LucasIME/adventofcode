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

  def energized(matrix, row \\ 0, col \\ -1, dir \\ :right) do
    energized(matrix, row, col, dir, MapSet.new())
  end

  def visited_size(visited) do
    seen =
      visited
      |> Enum.map(fn {row, col, _dir} -> {row, col} end)
      |> MapSet.new()
      |> MapSet.size()

    # have to disconsider the position out of the board where we start
    seen - 1
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    matrix =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.graphemes/1)
      |> Enum.map(&List.to_tuple/1)
      |> List.to_tuple()

    energized(matrix) |> visited_size()
  end

  def get_all_possible_starts(matrix) do
    last_row = tuple_size(matrix) - 1
    last_col = tuple_size(matrix |> elem(0)) - 1

    left_starts = for row <- 0..last_row, do: {row, -1, :right}
    right_starts = for row <- 0..last_row, do: {row, last_col + 1, :left}
    top_starts = for col <- 0..last_col, do: {-1, col, :down}
    bottom_starts = for col <- 0..last_col, do: {last_row + 1, col, :up}

    left_starts ++ right_starts ++ top_starts ++ bottom_starts
  end

  def part2(input \\ IO.stream(:stdio, :line)) do
    matrix =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.graphemes/1)
      |> Enum.map(&List.to_tuple/1)
      |> List.to_tuple()

    all_possible_starts = get_all_possible_starts(matrix)

    all_possible_starts
    |> Enum.map(fn {row, col, dir} -> energized(matrix, row, col, dir) end)
    |> Enum.map(&visited_size/1)
    |> Enum.max()
  end
end
