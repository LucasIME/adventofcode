defmodule AdventOfCode.Q18 do
  def get_next(row, col, dir, steps) do
    case dir do
      "R" -> {row, col + steps}
      "U" -> {row - steps, col}
      "L" -> {row, col - steps}
      "D" -> {row + steps, col}
    end
  end

  # only works because I know they'll always be vertical or horizontal lines
  def edge_len({x1, y1}, {x2, y2}) do
    abs(y2 - y1) + abs(x2 - x1)
  end

  def perimeter(vertices) do
    rotated =
      vertices
      |> Enum.zip(vertices |> rotated_vertices(1))

    rotated
    |> Enum.map(fn {a, b} -> edge_len(a, b) end)
    |> Enum.sum()
  end

  def to_vertices(steps) do
    initial_vertice = [{0, 0}]

    Enum.reduce(steps, initial_vertice, fn {dir, steps}, acc_vertices ->
      [{last_row, last_col} | _tail] = acc_vertices
      next_vertice = get_next(last_row, last_col, dir, steps)
      [next_vertice | acc_vertices]
    end)
  end

  def shoelace(vertices) do
    rotated =
      vertices
      |> Enum.zip(vertices |> rotated_vertices(1))

    rotated
    |> Enum.map(fn {a, b} -> shoelace_term(a, b) end)
    |> Enum.sum()
    |> Kernel.abs()
    |> Kernel./(2)
  end

  defp shoelace_term({y1, x1}, {y2, x2}) do
    x1 * y2 - x2 * y1
  end

  defp rotated_vertices(vertices, offset) do
    vertices
    |> Enum.drop(offset)
    |> Enum.concat(Enum.take(vertices, offset))
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    steps =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.split(&1, " "))
      |> Enum.map(fn [dir, raw_steps, _color] -> {dir, String.to_integer(raw_steps)} end)

    vertices = to_vertices(steps)
    perimeter = vertices |> perimeter()
    shoelace = shoelace(vertices)

    # Pick's theorem
    area = (shoelace + div(perimeter, 2) + 1) |> trunc()
    area
  end

  def color_number_to_direction(color_number) do
    case color_number do
      "0" -> "R"
      "1" -> "D"
      "2" -> "L"
      "3" -> "U"
    end
  end

  def color_to_instruction(color_with_parens) do
    # dropping (#
    color_numbers = color_with_parens |> String.graphemes() |> Enum.drop(2)
    reversed_color_numbers = color_numbers |> Enum.reverse() |> Enum.drop(1)
    [direction_color_number | remaining_numbers] = reversed_color_numbers

    raw_hex = remaining_numbers |> Enum.reverse() |> List.to_string()
    real_hex = String.to_integer(raw_hex, 16)

    {color_number_to_direction(direction_color_number), real_hex}
  end

  def part2(input \\ IO.stream(:stdio, :line)) do
    steps =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.split(&1, " "))
      |> Enum.map(fn [_dir, _raw_steps, color] -> color_to_instruction(color) end)

    vertices = to_vertices(steps)
    perimeter = vertices |> perimeter()
    shoelace = shoelace(vertices)

    # Pick's theorem: A = inside + (perimeter/2) - 1
    # inside = A - (perimeter/2) + 1
    inside = (shoelace - div(perimeter, 2) + 1) |> trunc()
    inside + perimeter
  end
end
