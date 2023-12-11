defmodule AdventOfCode.Q11 do
  def all_empty([]) do
    true
  end

  def all_empty([c | tail]) do
    c == "." and all_empty(tail)
  end

  def expand_rows([]) do
    []
  end

  def expand_rows([row | tail]) do
    if all_empty(row) do
      [row, row | expand_rows(tail)]
    else
      [row | expand_rows(tail)]
    end
  end

  def transpose(matrix) do
    matrix
    |> Enum.zip()
    |> Enum.map(&Tuple.to_list/1)
  end

  def expand_cols(universe) do
    universe
    |> transpose()
    |> expand_rows()
    |> transpose()
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

  def get_every_pair_dist(points) do
    all_pairs = for p1 <- points, p2 <- points, do: {p1, p2}

    all_pairs
    |> Enum.map(fn {{row1, col1}, {row2, col2}} -> abs(row2 - row1) + abs(col2 - col1) end)
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    universe =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.graphemes/1)

    expanded_universe =
      universe
      |> expand_rows()
      |> expand_cols()

    points = get_points(expanded_universe)
    every_pair_dist = get_every_pair_dist(points)

    every_pair_dist
    |> Enum.sum()
    |> div(2)
  end
end
