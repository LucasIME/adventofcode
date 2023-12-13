defmodule AdventOfCode.Q13 do
  def transpose(matrix) do
    matrix
    |> Enum.zip()
    |> Enum.map(&Tuple.to_list/1)
  end

  def rev_get_points([]) do
    {0, false}
  end

  def rev_get_points([_some]) do
    {0, false}
  end

  def rev_get_points(grid) do
    if grid == Enum.reverse(grid) do
      {div(length(grid), 2), true}
    else
      rev_get_points(tl(grid))
    end
  end

  def get_points([]) do
    {0, false}
  end

  def get_points([_some]) do
    {0, false}
  end

  def get_points(grid) do
    if grid == Enum.reverse(grid) do
      {div(length(grid), 2), true}
    else
      {points, is_match} = get_points(tl(grid))

      if is_match do
        {1 + points, true}
      else
        {0, false}
      end
    end
  end

  def rev_summarize(grid) do
    {horizontal_reflection, matches?} = rev_get_points(grid |> Enum.reverse())

    if matches? do
      100 * horizontal_reflection
    else
      {vertical_reflection, matches?} = rev_get_points(transpose(grid) |> Enum.reverse())

      if matches? do
        vertical_reflection
      else
        0
      end
    end
  end

  def summarize(grid) do
    {horizontal_reflection, matches?} = get_points(grid)

    if matches? do
      100 * horizontal_reflection
    else
      {vertical_reflection, matches?} = get_points(transpose(grid))

      if matches? do
        vertical_reflection
      else
        rev_summarize(grid)
      end
    end
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    grid_list =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.chunk_while(
        [],
        fn line, acc ->
          if line == "" do
            {:cont, Enum.reverse(acc), []}
          else
            {:cont, [line | acc]}
          end
        end,
        fn
          [] -> {:cont, []}
          acc -> {:cont, Enum.reverse(acc), []}
        end
      )
      |> Enum.map(fn grid -> Enum.map(grid, &String.graphemes/1) end)

    grid_list
    |> Enum.map(&summarize/1)
    |> Enum.sum()
  end
end
