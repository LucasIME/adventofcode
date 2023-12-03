defmodule AdventOfCode.Q3 do
  def to_tuple_matrix(lines) do
    List.to_tuple(Enum.map(lines, &List.to_tuple/1))
  end

  def m_get(matrix, line, col) do
    elem(elem(matrix, line), col)
  end

  def in_bounds?(tuple_matrix, {row, col}) do
    row >= 0 and row < tuple_size(tuple_matrix) and col >= 0 and
      col < tuple_size(elem(tuple_matrix, 0))
  end

  def is_special_char?(tuple_matrix, {row, col}) do
    case m_get(tuple_matrix, row, col) do
      "." ->
        false

      any ->
        case Integer.parse(any) do
          :error -> true
          {_num, _} -> false
        end
    end
  end

  def has_neighbour?(tuple_matrix, row, col) do
    for newRow <- [row - 1, row, row + 1],
        newCol <- [col - 1, col, col + 1],
        pos = {newRow, newCol},
        in_bounds?(tuple_matrix, pos) &&
          (row != elem(pos, 0) || col != elem(pos, 1)) &&
          is_special_char?(tuple_matrix, pos) do
      pos
    end
    |> Enum.any?()
  end

  def board_sum(lines) do
    tuple_matrix = to_tuple_matrix(lines)

    line_num = tuple_size(tuple_matrix)
    col_num = tuple_size(elem(tuple_matrix, 0))

    {result, _acc, _should_add} =
      Enum.reduce(0..(line_num - 1), {0, 0, false}, fn line, {res, acc, should_add} ->
        {res, acc, should_add} =
          Enum.reduce(0..(col_num - 1), {res, acc, should_add}, fn col, {res, acc, should_add} ->
            case m_get(tuple_matrix, line, col) do
              "." ->
                if should_add do
                  {res + acc, 0, false}
                else
                  {res, 0, false}
                end

              any ->
                case Integer.parse(any) do
                  :error ->
                    {res + acc, 0, false}

                  {num, _} ->
                    {res, 10 * acc + num, should_add || has_neighbour?(tuple_matrix, line, col)}
                end
            end
          end)

        if should_add do
          {res + acc, 0, false}
        else
          {res, acc, should_add}
        end
      end)

    result
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    input
    |> Enum.map(&String.trim/1)
    |> Enum.map(&String.graphemes/1)
    |> board_sum
  end
end
