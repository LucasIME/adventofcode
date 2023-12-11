defmodule AdventOfCode.Q11 do
  def all_empty([]) do
    true
  end

  def all_empty([c | tail]) do
    c == "." and all_empty(tail)
  end

  def transpose(matrix) do
    matrix
    |> Enum.zip()
    |> Enum.map(&Tuple.to_list/1)
  end

  def get_points([], out, _row, _col) do
    out
  end

  def get_points([char | tail], out, row, col) when is_binary(char) do
    new_out =
      case char do
        "#" -> [{row, col} | out]
        _ -> out
      end

    get_points(tail, new_out, row, col + 1)
  end

  def get_points([line | tail], out, row, col) when is_list(line) do
    new_out = get_points(line, out, row, col)
    get_points(tail, new_out, row + 1, col)
  end

  def get_points(matrix) do
    get_points(matrix, [], 0, 0)
  end

  def get_empty_rows([], out, _row) do
    out
  end

  def get_empty_rows([line | tail], out, row) when is_list(line) do
    new_out =
      case all_empty(line) do
        true -> MapSet.put(out, row)
        false -> out
      end

    get_empty_rows(tail, new_out, row + 1)
  end

  def get_empty_rows(matrix) do
    get_empty_rows(matrix, MapSet.new(), 0)
  end

  def get_empty_rows_and_cols(universe) do
    empty_rows = get_empty_rows(universe)
    empty_cols = get_empty_rows(universe |> transpose())

    {empty_rows, empty_cols}
  end

  def get_every_pair_dist(points, empty_rows, empty_cols, multiplier) do
    all_pairs = for p1 <- points, p2 <- points, do: {p1, p2}

    all_pairs
    |> Enum.map(fn {{row1, col1}, {row2, col2}} ->
      result = abs(row2 - row1) + abs(col2 - col1)
      min_row = min(row1, row2)
      max_row = max(row1, row2)
      min_col = min(col1, col2)
      max_col = max(col1, col2)

      result =
        Enum.reduce(empty_cols, result, fn col, acc ->
          if col > min_col and col < max_col do
            acc + (multiplier - 1)
          else
            acc
          end
        end)

      result =
        Enum.reduce(empty_rows, result, fn row, acc ->
          if row > min_row and row < max_row do
            acc + (multiplier - 1)
          else
            acc
          end
        end)

      result
    end)
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    universe =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.graphemes/1)

    {empty_rows, empty_cols} = get_empty_rows_and_cols(universe)
    points = get_points(universe)
    every_pair_dist = get_every_pair_dist(points, empty_rows, empty_cols, 2)

    every_pair_dist
    |> Enum.sum()
    |> div(2)
  end

  def part2(input \\ IO.stream(:stdio, :line), multiplier \\ 1_000_000) do
    universe =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.graphemes/1)

    {empty_rows, empty_cols} = get_empty_rows_and_cols(universe)
    points = get_points(universe)
    every_pair_dist = get_every_pair_dist(points, empty_rows, empty_cols, multiplier)

    every_pair_dist
    |> Enum.sum()
    |> div(2)
  end
end
