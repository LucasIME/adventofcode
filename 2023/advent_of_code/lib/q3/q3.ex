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

  def get_star_neighbours(tuple_matrix, row, col) do
    response =
      for newRow <- [row - 1, row, row + 1],
          newCol <- [col - 1, col, col + 1],
          pos = {newRow, newCol},
          in_bounds?(tuple_matrix, pos) &&
            (row != elem(pos, 0) || col != elem(pos, 1)) &&
            m_get(tuple_matrix, newRow, newCol) == "*" do
        pos
      end

    response
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

  def reduce_board_per_value(
        tuple_matrix,
        row,
        col,
        cur_results,
        accumulated_value,
        cur_dots_positions
      ) do
    case m_get(tuple_matrix, row, col) do
      "." ->
        {add_positions_to_cur_map(cur_results, accumulated_value, cur_dots_positions), 0,
         MapSet.new()}

      any ->
        case Integer.parse(any) do
          :error ->
            {add_positions_to_cur_map(cur_results, accumulated_value, cur_dots_positions), 0,
             MapSet.new()}

          {num, _} ->
            {cur_results, 10 * accumulated_value + num,
             Enum.reduce(
               get_star_neighbours(tuple_matrix, row, col),
               cur_dots_positions,
               fn cur_pos, cur_dots_positions ->
                 MapSet.put(cur_dots_positions, cur_pos)
               end
             )}
        end
    end
  end

  def reduce_board_per_line(tuple_matrix, row, cur_results) do
    col_num = tuple_size(elem(tuple_matrix, 0))

    {res, acc, cur_dots_positions} =
      Enum.reduce(
        0..(col_num - 1),
        {cur_results, 0, MapSet.new()},
        fn col, {res, acc, cur_dots_positions} ->
          reduce_board_per_value(tuple_matrix, row, col, res, acc, cur_dots_positions)
        end
      )

    add_positions_to_cur_map(res, acc, cur_dots_positions)
  end

  def add_positions_to_cur_map(pos_to_values, value, cur_dots_positions_set) do
    Enum.reduce(cur_dots_positions_set, pos_to_values, fn cur_dot_pos, pos_to_values ->
      Map.put(
        pos_to_values,
        cur_dot_pos,
        MapSet.put(Map.get(pos_to_values, cur_dot_pos, MapSet.new()), value)
      )
    end)
  end

  def board_gear_ratios(lines) do
    tuple_matrix = to_tuple_matrix(lines)

    line_num = tuple_size(tuple_matrix)

    Enum.reduce(0..(line_num - 1), %{}, fn row, res ->
      reduce_board_per_line(tuple_matrix, row, res)
    end)
    |> Enum.filter(fn {_pos, map_set} -> MapSet.size(map_set) == 2 end)
    |> Map.new()
  end

  def part2(input \\ IO.stream(:stdio, :line)) do
    input
    |> Enum.map(&String.trim/1)
    |> Enum.map(&String.graphemes/1)
    |> board_gear_ratios()
    |> Map.values()
    |> Enum.map(&MapSet.to_list/1)
    |> Enum.map(fn [first_val, second_val] -> first_val * second_val end)
    |> Enum.sum()
  end
end
