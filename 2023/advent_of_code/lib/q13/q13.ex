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
    if rem(length(grid), 2) == 0 and grid == Enum.reverse(grid) do
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

  def all_summaries(grid) do
    {rowp, row_ok} = get_points(grid)
    row = {100 * rowp, row_ok}

    col = get_points(transpose(grid))

    {rev_rowp, rev_row_ok} = rev_get_points(grid |> Enum.reverse())
    rev_row = {100 * rev_rowp, rev_row_ok}

    rev_col = rev_get_points(transpose(grid) |> Enum.reverse())

    [row, col, rev_row, rev_col]
    |> Enum.filter(fn {_p, ok?} -> ok? end)
    |> Enum.map(fn {p, _} -> p end)
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

  def invert_c(c) do
    case c do
      "#" -> "."
      "." -> "#"
    end
  end

  def invert(matrix, row, col) do
    tuple_matrix = matrix |> Enum.map(&List.to_tuple/1) |> List.to_tuple()

    tuple_matrix =
      put_elem(
        tuple_matrix,
        row,
        put_elem(tuple_matrix |> elem(row), col, invert_c(tuple_matrix |> elem(row) |> elem(col)))
      )

    Tuple.to_list(tuple_matrix) |> Enum.map(&Tuple.to_list/1)
  end

  def all_variations(matrix) do
    all_modified =
      for row <- 0..(length(matrix) - 1),
          col <- 0..(length(hd(matrix)) - 1),
          do: invert(matrix, row, col)

    all_modified
  end

  def summarize_different(grid) do
    original_val = summarize(grid)

    resp =
      grid
      |> all_variations()
      |> Enum.flat_map(fn matrix ->
        summaries = all_summaries(matrix)

        filtered_summaries =
          summaries
          |> Enum.filter(fn matrix_val ->
            matrix_val != original_val and matrix_val != 0
          end)

        filtered_summaries
      end)

    # Not sure why I have to take the max and not just any? In theory these were not supposed to be different...
    resp
    |> Enum.max()
  end

  def part2(input \\ IO.stream(:stdio, :line)) do
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
    |> Enum.map(&summarize_different/1)
    |> Enum.sum()
  end
end
