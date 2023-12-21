defmodule AdventOfCode.Q21 do
  def to_sparse_matrix(input) do
    input
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

  def normalized_position({row, col}, square_size) do
    new_row = (rem(row, square_size) + square_size) |> rem(square_size)
    new_col = (rem(col, square_size) + square_size) |> rem(square_size)

    {new_row, new_col}
  end

  def neighbours({row, col}, walls) do
    normal_neighs = [{row - 1, col}, {row + 1, col}, {row, col - 1}, {row, col + 1}]

    normal_neighs |> Enum.filter(fn pos -> not Map.has_key?(walls, pos) end)
  end

  def neighbours({row, col}, walls, square_size) do
    normal_neighs = [{row - 1, col}, {row + 1, col}, {row, col - 1}, {row, col + 1}]

    normal_neighs
    |> Enum.filter(fn pos ->
      normalize_pos = normalized_position(pos, square_size)
      not Map.has_key?(walls, normalize_pos)
    end)
  end

  def expand(positions, _walls, 0) do
    positions
  end

  def expand(positions, walls, steps) do
    new_set = positions |> Enum.flat_map(&neighbours(&1, walls)) |> Enum.into(MapSet.new())
    expand(new_set, walls, steps - 1)
  end

  def expand(positions, _walls, 0, _square_size) do
    positions
  end

  def expand(positions, walls, steps, square_size) do
    new_set =
      positions |> Enum.flat_map(&neighbours(&1, walls, square_size)) |> Enum.into(MapSet.new())

    expand(new_set, walls, steps - 1, square_size)
  end

  def walk_steps(start, walls, steps) do
    expand(MapSet.new([start]), walls, steps)
  end

  def walk_steps(start, walls, steps, square_size) do
    expand(MapSet.new([start]), walls, steps, square_size)
  end

  def part1(input \\ IO.stream(:stdio, :line), steps \\ 64) do
    input_lines = input |> Enum.map(&String.trim/1) |> Enum.to_list()
    sparse = to_sparse_matrix(input_lines)

    start = sparse |> Enum.find(fn {_pos, char} -> char == "S" end) |> elem(0)

    sparse = Map.delete(sparse, start)

    walk_steps(start, sparse, steps) |> MapSet.size()
  end

  def interpolate([p1, p2, p3]) do
    c = p1
    a_plus_b = p2 - c
    four_a_plus_two_b = p3 - c
    two_a = four_a_plus_two_b - 2 * a_plus_b
    a = two_a / 2
    b = a_plus_b - a

    fn x -> a * (x * x) + b * x + c end
  end

  def part2(input \\ IO.stream(:stdio, :line)) do
    input_lines = input |> Enum.map(&String.trim/1) |> Enum.to_list()
    sparse = to_sparse_matrix(input_lines)
    start = sparse |> Enum.find(fn {_pos, char} -> char == "S" end) |> elem(0)
    sparse = Map.delete(sparse, start)

    steps = 26_501_365

    grid_size = length(input_lines)
    complete_grids = div(steps, grid_size)
    remaining_steps = rem(steps, grid_size)

    # Grid is square and we have a clean path from start to all the edges. So result should be quadratic in the form of:
    # [remaining_steps, remaining_steps + grid_size, remaining_steps + 2*grid_size, ...]
    first_three_points =
      for i <- 0..2,
          do:
            walk_steps(start, sparse, i * grid_size + remaining_steps, grid_size) |> MapSet.size()

    output_function = interpolate(first_three_points)

    output_function.(complete_grids) |> trunc()
  end
end
