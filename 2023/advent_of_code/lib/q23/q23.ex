defmodule AdventOfCode.Q23 do
  def get_raw_neighbours(grid, row, col) do
    case grid |> elem(row) |> elem(col) do
      ">" -> [{row, col + 1}]
      "v" -> [{row + 1, col}]
      "<" -> [{row, col - 1}]
      "^" -> [{row - 1, col}]
      _ -> [{row, col + 1}, {row + 1, col}, {row, col - 1}, {row - 1, col}]
    end
  end

  def get_raw_neighbours2(_grid, row, col) do
    [{row, col + 1}, {row + 1, col}, {row, col - 1}, {row - 1, col}]
  end

  def is_valid_pos?(grid, row, col) do
    inside? =
      row >= 0 and row < tuple_size(grid) and col >= 0 and col < tuple_size(grid |> elem(row))

    inside? and not (grid |> elem(row) |> elem(col) == "#")
  end

  def get_neighbours(grid, row, col) do
    get_raw_neighbours(grid, row, col)
    |> Enum.filter(fn {cur_row, cur_col} -> is_valid_pos?(grid, cur_row, cur_col) end)
  end

  def get_neighbours2(grid, row, col) do
    get_raw_neighbours2(grid, row, col)
    |> Enum.filter(fn {cur_row, cur_col} -> is_valid_pos?(grid, cur_row, cur_col) end)
  end

  def get_longest_path(_grid, cur, target, visited) when cur == target do
    MapSet.size(visited)
  end

  def get_longest_path(grid, cur, target, visited) do
    if MapSet.member?(visited, cur) do
      0
    else
      new_visited = MapSet.put(visited, cur)
      {row, col} = cur
      neighbours = get_neighbours(grid, row, col)

      max_path =
        neighbours |> Enum.map(&get_longest_path(grid, &1, target, new_visited)) |> Enum.max()

      max_path
    end
  end

  def get_longest_path(grid, start, target) do
    get_longest_path(grid, start, target, MapSet.new())
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    grid =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.graphemes/1)
      |> Enum.map(&List.to_tuple/1)
      |> List.to_tuple()

    get_longest_path(grid, {0, 1}, {tuple_size(grid) - 1, tuple_size(grid |> elem(0)) - 2})
  end

  def get_crosses(grid) do
    all_points =
      for row <- 0..(tuple_size(grid) - 1),
          col <- 0..(tuple_size(grid |> elem(row)) - 1),
          grid |> elem(row) |> elem(col) != "#",
          do: {row, col}

    all_points
    |> Enum.filter(fn {row, col} ->
      neighs =
        get_raw_neighbours2(grid, row, col)
        |> Enum.filter(fn {cur_row, cur_col} -> is_valid_pos?(grid, cur_row, cur_col) end)

      length(neighs) > 2
    end)
  end

  def walk(grid, cur, visited, all_cross) do
    if MapSet.member?(all_cross, cur) do
      {cur, MapSet.size(visited)}
    else
      if MapSet.member?(visited, cur) do
        {:none, 0}
      else
        new_visited = MapSet.put(visited, cur)
        {row, col} = cur
        neighbours = get_neighbours2(grid, row, col)

        max_path =
          neighbours
          |> Enum.map(&walk(grid, &1, new_visited, all_cross))
          |> Enum.max_by(fn {_target, path} -> path end)

        max_path
      end
    end
  end

  def get_cross_distances(_grid, _all_cross, [], paths_so_far) do
    paths_so_far
  end

  def get_cross_distances(grid, all_cross, [{row, col} | tail], paths_so_far) do
    neighs = get_neighbours2(grid, row, col)

    adjacent_cross_and_dist =
      neighs |> Enum.map(&walk(grid, &1, MapSet.new([{row, col}]), all_cross)) |> Enum.into(%{})

    new_paths = Map.put(paths_so_far, {row, col}, adjacent_cross_and_dist)
    get_cross_distances(grid, all_cross, tail, new_paths)
  end

  def get_cross_distances(grid, cross_points) do
    get_cross_distances(grid, cross_points |> Enum.into(MapSet.new()), cross_points, Map.new())
  end

  def get_longest_path2(_graph, cur, target, _visited, steps) when cur == target do
    steps
  end

  def get_longest_path2(graph, cur, target, visited, steps) do
    if MapSet.member?(visited, cur) do
      0
    else
      new_visited = MapSet.put(visited, cur)
      neighbours = graph[cur]

      max_path =
        neighbours
        |> Enum.map(fn {pos, dist} ->
          get_longest_path2(graph, pos, target, new_visited, steps + dist)
        end)
        |> Enum.max()

      max_path
    end
  end

  def get_longest_path2(graph, start, target) do
    get_longest_path2(graph, start, target, MapSet.new(), 0)
  end

  def part2(input \\ IO.stream(:stdio, :line)) do
    grid =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.graphemes/1)
      |> Enum.map(&List.to_tuple/1)
      |> List.to_tuple()

    start = {0, 1}
    target = {tuple_size(grid) - 1, tuple_size(grid |> elem(0)) - 2}

    cross_points = [start] ++ get_crosses(grid) ++ [target]
    cross_distances = get_cross_distances(grid, cross_points)

    get_longest_path2(
      cross_distances,
      {0, 1},
      {tuple_size(grid) - 1, tuple_size(grid |> elem(0)) - 2}
    )
  end
end
