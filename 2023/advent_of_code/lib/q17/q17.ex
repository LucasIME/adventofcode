defmodule AdventOfCode.Q17 do
  def is_valid?(grid, row, col) do
    row >= 0 and row < tuple_size(grid) and col >= 0 and col < tuple_size(grid |> elem(row))
  end

  def initialize_distances(grid) do
    last_row = tuple_size(grid) - 1
    last_col = tuple_size(grid |> elem(0)) - 1

    cells =
      for row <- 0..last_row,
          col <- 0..last_col,
          dir <- [:up, :down, :left, :right],
          do: {row, col, dir}

    dists = cells |> Enum.map(fn pos -> {pos, :infinity} end) |> Enum.into(%{})
    dists = Map.put(dists, {0, 0, :left}, 0)
    dists
  end

  def find_min_distance(unvisited, distances) do
    unvisited
    |> MapSet.to_list()
    |> Enum.map(fn node -> {node, Map.get(distances, node)} end)
    |> Enum.min_by(fn {_node, distance} -> distance end)
  end

  def update_distances(pq, distances, neigh_with_dist) do
    Enum.reduce(neigh_with_dist, {pq, distances}, fn {neigh, new_dist}, {acc_pq, acc_dist} ->
      old_dist = Map.get(acc_dist, neigh)

      if new_dist < old_dist do
        {PriorityQueue.put(acc_pq, {new_dist, neigh}), Map.put(acc_dist, neigh, new_dist)}
      else
        {acc_pq, acc_dist}
      end
    end)
  end

  def get_next(row, col, dir) do
    case dir do
      :up -> {row - 1, col}
      :down -> {row + 1, col}
      :left -> {row, col - 1}
      :right -> {row, col + 1}
    end
  end

  def get_dist_after_steps(_grid, _row, _col, _dir, _cur_dist, 10) do
    []
  end

  def get_dist_after_steps(grid, row, col, dir, cur_dist, steps) do
    {next_row, next_col} = get_next(row, col, dir)

    if is_valid?(grid, next_row, next_col) do
      new_dist = cur_dist + (grid |> elem(next_row) |> elem(next_col))

      [
        {{next_row, next_col, dir}, new_dist}
        | get_dist_after_steps(grid, next_row, next_col, dir, new_dist, steps - 1)
      ]
    else
      []
    end
  end

  def get_possible_next_dirs(dir) do
    case dir do
      :up -> [:left, :right]
      :down -> [:left, :right]
      :left -> [:up, :down]
      :right -> [:up, :down]
    end
  end

  def get_neighs(grid, row, col, dir, cur_dist, step_fun) do
    possible_dirs = get_possible_next_dirs(dir)
    possible_dirs |> Enum.flat_map(&step_fun.(grid, row, col, &1, cur_dist))
  end

  def find_shortest(grid, distances, priority_queue, target, step_fun) do
    {{cur_dist, {row, col, dir}}, new_priority_queue} = PriorityQueue.pop!(priority_queue)

    if {row, col} == target do
      cur_dist
    else
      neigh_with_dist = get_neighs(grid, row, col, dir, cur_dist, step_fun)

      {new_priority_queue, updated_distances} =
        update_distances(new_priority_queue, distances, neigh_with_dist)

      find_shortest(grid, updated_distances, new_priority_queue, target, step_fun)
    end
  end

  def min_dist(grid, step_fun) do
    distances = initialize_distances(grid)

    target = {tuple_size(grid) - 1, tuple_size(grid |> elem(0)) - 1}
    priority_queue = [{0, {0, 0, :left}}, {0, {0, 0, :up}}] |> Enum.into(PriorityQueue.new())

    find_shortest(grid, distances, priority_queue, target, step_fun)
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    grid =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.graphemes/1)
      |> Enum.map(&Enum.map(&1, fn char -> String.to_integer(char) end))
      |> Enum.map(&List.to_tuple/1)
      |> List.to_tuple()

    min_dist(grid, fn my_grid, row, col, dir, cur_dist ->
      get_dist_between_steps(my_grid, row, col, dir, cur_dist, 1, 3)
    end)
  end

  def get_dist_between_steps(grid, row, col, dir, cur_dist, min_step, max_step, cur_step \\ 1)

  def get_dist_between_steps(_grid, _row, _col, _dir, _cur_dist, _min_step, max_step, cur_step)
      when cur_step > max_step do
    []
  end

  def get_dist_between_steps(grid, row, col, dir, cur_dist, min_step, max_step, cur_step) do
    {next_row, next_col} = get_next(row, col, dir)

    if is_valid?(grid, next_row, next_col) do
      new_dist = cur_dist + (grid |> elem(next_row) |> elem(next_col))

      my_contribution =
        if cur_step >= min_step do
          [{{next_row, next_col, dir}, new_dist}]
        else
          []
        end

      my_contribution ++
        get_dist_between_steps(
          grid,
          next_row,
          next_col,
          dir,
          new_dist,
          min_step,
          max_step,
          cur_step + 1
        )
    else
      []
    end
  end

  def part2(input \\ IO.stream(:stdio, :line)) do
    grid =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&String.graphemes/1)
      |> Enum.map(&Enum.map(&1, fn char -> String.to_integer(char) end))
      |> Enum.map(&List.to_tuple/1)
      |> List.to_tuple()

    min_dist(grid, fn my_grid, row, col, dir, cur_dist ->
      get_dist_between_steps(my_grid, row, col, dir, cur_dist, 4, 10)
    end)
  end
end
