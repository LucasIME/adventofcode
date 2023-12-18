defmodule AdventOfCode.Q10 do
  def get_start(board) do
    row_with_s = board |> Enum.find_index(fn row -> row |> Enum.member?("S") end)
    col_in_row_with_s = Enum.at(board, row_with_s) |> Enum.find_index(fn cell -> cell == "S" end)
    {row_with_s, col_in_row_with_s}
  end

  def graph_get_or_nil(board, row, col) do
    board |> get_or_nil(row) |> get_or_nil(col)
  end

  def get_or_nil(tuple, index) do
    if tuple != nil and index >= 0 and index < tuple_size(tuple) do
      elem(tuple, index)
    else
      nil
    end
  end

  def sum_tuples(tuple1, tuple2) do
    Enum.zip_with(tuple1 |> Tuple.to_list(), tuple2 |> Tuple.to_list(), &+/2)
  end

  def get_up_neigh(board, {row, col}) do
    cur_cell = graph_get_or_nil(board, row, col)

    neigh_pos = {row - 1, col}

    case graph_get_or_nil(board, row - 1, col) do
      nil ->
        nil

      cell ->
        case {cur_cell, cell} do
          {"S", "7"} -> neigh_pos
          {"S", "|"} -> neigh_pos
          {"S", "F"} -> neigh_pos
          {"|", "7"} -> neigh_pos
          {"|", "|"} -> neigh_pos
          {"|", "F"} -> neigh_pos
          {"J", "7"} -> neigh_pos
          {"J", "|"} -> neigh_pos
          {"J", "F"} -> neigh_pos
          {"L", "7"} -> neigh_pos
          {"L", "|"} -> neigh_pos
          {"L", "F"} -> neigh_pos
          _ -> nil
        end
    end
  end

  def get_down_neigh(board, {row, col}) do
    cur_cell = graph_get_or_nil(board, row, col)

    neigh_pos = {row + 1, col}

    case graph_get_or_nil(board, row + 1, col) do
      nil ->
        nil

      cell ->
        case {cur_cell, cell} do
          {"S", "J"} -> neigh_pos
          {"S", "|"} -> neigh_pos
          {"S", "L"} -> neigh_pos
          {"|", "J"} -> neigh_pos
          {"|", "|"} -> neigh_pos
          {"|", "L"} -> neigh_pos
          {"7", "J"} -> neigh_pos
          {"7", "|"} -> neigh_pos
          {"7", "L"} -> neigh_pos
          {"F", "J"} -> neigh_pos
          {"F", "|"} -> neigh_pos
          {"F", "L"} -> neigh_pos
          _ -> nil
        end
    end
  end

  def get_east_neigh(board, {row, col}) do
    cur_cell = graph_get_or_nil(board, row, col)

    neigh_pos = {row, col + 1}

    case graph_get_or_nil(board, row, col + 1) do
      nil ->
        nil

      cell ->
        case {cur_cell, cell} do
          {"S", "J"} -> neigh_pos
          {"S", "-"} -> neigh_pos
          {"S", "7"} -> neigh_pos
          {"-", "J"} -> neigh_pos
          {"-", "-"} -> neigh_pos
          {"-", "7"} -> neigh_pos
          {"L", "J"} -> neigh_pos
          {"L", "-"} -> neigh_pos
          {"L", "7"} -> neigh_pos
          {"F", "J"} -> neigh_pos
          {"F", "-"} -> neigh_pos
          {"F", "7"} -> neigh_pos
          _ -> nil
        end
    end
  end

  def get_west_neigh(board, {row, col}) do
    cur_cell = graph_get_or_nil(board, row, col)

    neigh_pos = {row, col - 1}

    case graph_get_or_nil(board, row, col - 1) do
      nil ->
        nil

      cell ->
        case {cur_cell, cell} do
          {"S", "F"} -> neigh_pos
          {"S", "-"} -> neigh_pos
          {"S", "L"} -> neigh_pos
          {"-", "F"} -> neigh_pos
          {"-", "-"} -> neigh_pos
          {"-", "L"} -> neigh_pos
          {"J", "F"} -> neigh_pos
          {"J", "-"} -> neigh_pos
          {"J", "L"} -> neigh_pos
          {"7", "F"} -> neigh_pos
          {"7", "-"} -> neigh_pos
          {"7", "L"} -> neigh_pos
          _ -> nil
        end
    end
  end

  def get_neighs(board, {row, col}) do
    neighs = [
      get_up_neigh(board, {row, col}),
      get_down_neigh(board, {row, col}),
      get_east_neigh(board, {row, col}),
      get_west_neigh(board, {row, col})
    ]

    Enum.filter(neighs, fn neigh -> neigh != nil end)
  end

  def add_edges(graph, _source, []) do
    graph
  end

  def add_edges(graph, source, [neigh | neighs]) do
    cur_src_neighs = Map.get(graph, source, MapSet.new())
    cur_src_neighs = MapSet.put(cur_src_neighs, neigh)
    graph = Map.put(graph, source, cur_src_neighs)

    cur_neigh_neighs = Map.get(graph, neigh, MapSet.new())
    cur_neigh_neighs = MapSet.put(cur_neigh_neighs, source)
    graph = Map.put(graph, neigh, cur_neigh_neighs)

    add_edges(graph, source, neighs)
  end

  def get_graph(_board, [], _visited_set, out_graph) do
    out_graph
  end

  def get_graph(board, [{row, col} | tail], visited_set, out_graph) do
    if MapSet.member?(visited_set, {row, col}) do
      get_graph(board, tail, visited_set, out_graph)
    else
      visited_set = MapSet.put(visited_set, {row, col})

      neighs = get_neighs(board, {row, col})
      out_graph = add_edges(out_graph, {row, col}, neighs)

      get_graph(board, neighs ++ tail, visited_set, out_graph)
    end
  end

  def get_graph(board, {row, col}) do
    get_graph(board, [{row, col}], MapSet.new(), %{})
  end

  def get_loop_size(graph, start_pos, visited_set, steps) do
    if MapSet.member?(visited_set, start_pos) do
      steps
    else
      visited_set = MapSet.put(visited_set, start_pos)

      neighs =
        Map.get(graph, start_pos, MapSet.new())
        |> Enum.filter(fn neigh -> not MapSet.member?(visited_set, neigh) end)

      if Enum.empty?(neighs) do
        steps
      else
        # assumes that there is only one neighbor, excep for very first position where it doesn't matter.
        get_loop_size(graph, hd(neighs), visited_set, steps + 1)
      end
    end
  end

  def get_loop_size(graph, start_pos) do
    get_loop_size(graph, start_pos, MapSet.new(), 1)
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    raw_board =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.graphemes/1)

    start_pos = get_start(raw_board)

    board = raw_board |> Enum.map(&List.to_tuple/1) |> List.to_tuple()

    graph = get_graph(board, start_pos)
    loop_size = get_loop_size(graph, start_pos)
    div(loop_size, 2)
  end

  def shoelace(vertices) do
    pairs =
      vertices
      |> Enum.zip(vertices |> rotated_vertices(1))

    pairs
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

  # only works because I know they'll always be vertical or horizontal lines
  def edge_len({x1, y1}, {x2, y2}) do
    abs(y2 - y1) + abs(x2 - x1)
  end

  def perimeter(vertices) do
    rotated =
      vertices
      |> Enum.zip(vertices |> rotated_vertices(1))

    all =
      rotated
      |> Enum.map(fn {a, b} -> edge_len(a, b) end)
      |> Enum.sum()

    all
  end

  def dfs(graph, current_node, visited, path) do
    if MapSet.member?(visited, current_node) do
      path
    else
      new_visited = MapSet.put(visited, current_node)
      new_path = [current_node | path]

      neighbors =
        Map.get(graph, current_node, MapSet.new())
        |> Enum.filter(fn pos -> not MapSet.member?(visited, pos) end)

      if length(neighbors) == 0 do
        path
      else
        dfs(graph, hd(neighbors), new_visited, new_path)
      end
    end
  end

  def dfs(graph, start) do
    dfs(graph, start, MapSet.new(), [])
  end

  def part2(input \\ IO.stream(:stdio, :line)) do
    raw_board =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.graphemes/1)

    start_pos = get_start(raw_board)

    board = raw_board |> Enum.map(&List.to_tuple/1) |> List.to_tuple()

    graph = get_graph(board, start_pos)
    allowed_vertices = ["L", "7", "S", "J", "F"] |> MapSet.new()

    vertices =
      dfs(graph, start_pos)
      |> Enum.filter(fn {row, col} ->
        MapSet.member?(allowed_vertices, board |> elem(row) |> elem(col))
      end)

    perimeter = vertices |> perimeter()
    shoelace = shoelace(vertices)

    # Pick's theorem: A = inside + (perimeter/2) - 1
    # ->  inside = A - (perimeter/2) + 1
    inside = (shoelace - div(perimeter, 2) + 1) |> ceil()
    inside
  end
end
