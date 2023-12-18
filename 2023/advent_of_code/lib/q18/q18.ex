defmodule AdventOfCode.Q18 do
  def get_next(row, col, dir, steps) do
    case dir do
      "R" -> {row, col + steps}
      "U" -> {row - steps, col}
      "L" -> {row, col - steps}
      "D" -> {row + steps, col}
    end
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

  def get_next_n(_row, _col, _dir, 0) do
    []
  end

  def get_next_n(row, col, dir, steps) do
    next_vertice = get_next(row, col, dir, 1)
    {next_row, next_col} = next_vertice
    [next_vertice | get_next_n(next_row, next_col, dir, steps - 1)]
  end

  def to_outline(steps) do
    initial_vertice = [{0, 0}]

    Enum.reduce(steps, initial_vertice, fn {dir, steps}, acc_vertices ->
      [{last_row, last_col} | _tail] = acc_vertices
      next_vertices = get_next_n(last_row, last_col, dir, steps)
      Enum.reverse(next_vertices) ++ acc_vertices
    end)
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    steps =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.split(&1, " "))
      |> Enum.map(fn [dir, raw_steps, _color] -> {dir, String.to_integer(raw_steps)} end)

    outline = to_outline(steps)
    # first point double counted
    perimeter = length(outline) - 1

    vertices = to_vertices(steps)
    vertices = Enum.drop(vertices, 1)

    shoelace = shoelace(vertices)

    # Pick's theorem
    area = (shoelace + div(perimeter, 2) + 1) |> trunc()
    area
  end

  def color_number_to_direction(color_number)  do
    case color_number do
      "0" -> "R"
      "1" -> "D"
      "2" -> "L"
      "3" -> "U"
    end
  end

  def color_to_instruction(color_with_parens) do
    color_numbers = color_with_parens |> String.graphemes |>  Enum.drop(2) # dropping (#
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

    outline = to_outline(steps)
    # first point double counted
    perimeter = length(outline) - 1

    vertices = to_vertices(steps)
    vertices = Enum.drop(vertices, 1)

    shoelace = shoelace(vertices)

    # Pick's theorem
    area = (shoelace + div(perimeter, 2) + 1) |> trunc()
    area
  end
end
