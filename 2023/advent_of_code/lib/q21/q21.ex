defmodule AdventOfCode.Q21 do
  def to_sparse_matrix(input) do
    input
    |> Enum.map(&String.trim/1)
    |> Enum.with_index()
    |> Enum.reduce(%{}, fn {line, row}, acc_map ->
      line_map =
        line
        |> String.graphemes()
        |> Enum.with_index()
        |> Enum.reduce(%{}, fn {char, col}, acc_map2 ->
          if char != "." do
            Map.put(acc_map2, {row, col}, char)
          else
            acc_map2
          end
        end)

      Map.merge(acc_map, line_map)
    end)
  end

  def neighbours({row, col}, walls) do
    normal_neighs = [{row - 1, col}, {row + 1, col}, {row, col - 1}, {row, col + 1}]

    normal_neighs |> Enum.filter(fn pos -> not Map.has_key?(walls, pos) end)
  end

  def expand(positions, _walls, 0) do
    positions
  end

  def expand(positions, walls, steps) do
    new_set = positions |> Enum.flat_map(&neighbours(&1, walls)) |> Enum.into(MapSet.new())
    expand(new_set, walls, steps - 1)
  end

  def walk_steps(start, walls, steps) do
    expand(MapSet.new([start]), walls, steps)
  end

  def part1(input \\ IO.stream(:stdio, :line), steps \\ 64) do
    sparse = to_sparse_matrix(input)

    start = sparse |> Enum.find(fn {_pos, char} -> char == "S" end) |> elem(0)

    sparse = Map.delete(sparse, start)

    walk_steps(start, sparse, steps) |> MapSet.size()
  end
end
