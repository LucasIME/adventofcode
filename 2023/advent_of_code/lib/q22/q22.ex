defmodule AdventOfCode.Q22 do
  def to_brick(line) do
    [raw_start, raw_end] = String.split(line, "~")
    start = raw_start |> String.split(",") |> Enum.map(&String.to_integer/1) |> List.to_tuple()
    real_end = raw_end |> String.split(",") |> Enum.map(&String.to_integer/1) |> List.to_tuple()

    {start, real_end}
  end

  def get_brick_points(brick) do
    {{x1, y1, z1}, {x2, y2, z2}} = brick

    for x <- x1..x2, y <- y1..y2, z <- z1..z2, do: {x, y, z}
  end

  def get_points_falling_one(brick) do
    {{x1, y1, z1}, {x2, y2, z2}} = brick

    for x <- x1..x2, y <- y1..y2, z <- (z1 - 1)..(z2 - 1), do: {x, y, z}
  end

  def generate_point_to_bricks(indexed_bricks) do
    indexed_bricks
    |> Enum.flat_map(fn {brick, index} ->
      brick_points = get_brick_points(brick)
      brick_points |> Enum.map(fn point -> {point, index} end)
    end)
    |> Enum.into(%{})
  end

  def can_fall(points, point_to_bricks, cur_index) do
    has_bad_points? =
      points
      |> Enum.any?(fn {x, y, z} ->
        on_ground? = z == 0
        into_another? = Map.get(point_to_bricks, {x, y, z}, cur_index) != cur_index

        on_ground? or into_another?
      end)

    not has_bad_points?
  end

  def propagate_fall(indexed_bricks, point_to_bricks, processed_bricks \\ [])

  def propagate_fall([], point_to_bricks, processed_bricks) do
    {processed_bricks, point_to_bricks}
  end

  def propagate_fall([cur_brick_with_index | brick_tail], point_to_bricks, processed_bricks) do
    {cur_brick, cur_index} = cur_brick_with_index

    points_after_fall = get_points_falling_one(cur_brick)

    if can_fall(points_after_fall, point_to_bricks, cur_index) do
      cur_points = get_brick_points(cur_brick)

      cur_points_with_brick_removed =
        Enum.reduce(cur_points, point_to_bricks, fn point, acc -> Map.delete(acc, point) end)

      new_points_with_brick_added =
        Enum.reduce(points_after_fall, cur_points_with_brick_removed, fn point, acc ->
          Map.put(acc, point, cur_index)
        end)

      {{x1, y1, z1}, {x2, y2, z2}} = cur_brick
      fallen_range = {{x1, y1, z1 - 1}, {x2, y2, z2 - 1}}

      propagate_fall(
        [{fallen_range, cur_index} | brick_tail],
        new_points_with_brick_added,
        processed_bricks
      )
    else
      propagate_fall(brick_tail, point_to_bricks, [cur_brick_with_index | processed_bricks])
    end
  end

  def bricks_under_brick({brick, index}, point_to_brick) do
    fallen_points = get_points_falling_one(brick)

    fallen_points
    |> Enum.map(fn point -> Map.get(point_to_brick, point) end)
    |> Enum.filter(fn brick_index -> brick_index != nil and brick_index != index end)
    |> MapSet.new()
  end

  def create_bricks_to_bricks_under(indexed_bricks, point_to_brick) do
    indexed_bricks
    |> Enum.flat_map(fn indexed_brick ->
      {_, my_index} = indexed_brick

      bricks_under_brick(indexed_brick, point_to_brick)
      |> Enum.map(fn down_index -> {my_index, down_index} end)
    end)
    |> Enum.reduce(%{}, fn {key, value}, acc ->
      Map.update(acc, key, [value], fn existing_values ->
        [value | existing_values]
      end)
    end)
  end

  def invert(map) do
    map
    |> Enum.flat_map(fn {key, values} -> values |> Enum.map(fn value -> {value, key} end) end)
    |> Enum.reduce(%{}, fn {key, value}, acc ->
      Map.update(acc, key, [value], fn existing_values ->
        [value | existing_values]
      end)
    end)
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    indexed_bricks =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&to_brick/1)
      |> Enum.with_index()

    sorted_bricks =
      indexed_bricks |> Enum.sort_by(fn {{{_, _, z1}, {_, _, z2}}, _} -> min(z1, z2) end)

    point_to_bricks = generate_point_to_bricks(sorted_bricks)

    {after_indexed_bricks, after_point_to_brick} = propagate_fall(sorted_bricks, point_to_bricks)

    bricks_to_bricks_under =
      create_bricks_to_bricks_under(after_indexed_bricks, after_point_to_brick)

    irreplaceable_bricks =
      bricks_to_bricks_under
      |> Enum.map(fn {_key, values} ->
        case values do
          [unique_element] -> unique_element
          _ -> nil
        end
      end)
      |> Enum.filter(fn x -> x != nil end)
      |> MapSet.new()

    length(after_indexed_bricks) - (irreplaceable_bricks |> MapSet.size())
  end

  def count_fall(%MapSet{map: map}, _bricks_to_bricks_under, _already_dropped)
      when map_size(map) == 0 do
    0
  end

  def count_fall(indexes, bricks_to_bricks_under, already_dropped) do
    new_state =
      bricks_to_bricks_under
      |> Enum.map(fn {key, values} ->
        {key, Enum.filter(values, fn x -> not MapSet.member?(indexes, x) end)}
      end)
      |> Enum.into(%{})

    new_already_dropped = MapSet.union(indexes, already_dropped)

    drops = new_state |> Enum.count(fn {_key, values} -> values == [] end)

    dropped_indexes =
      new_state
      |> Enum.map(fn {key, values} ->
        case values do
          [] -> key
          _ -> nil
        end
      end)
      |> Enum.filter(fn x -> x != nil end)
      |> Enum.filter(fn x -> not MapSet.member?(new_already_dropped, x) end)
      |> MapSet.new()

    new_state =
      new_state
      |> Enum.filter(fn {_key, values} ->
        case values do
          [] -> false
          _ -> true
        end
      end)
      |> Enum.into(%{})

    drops + count_fall(dropped_indexes, new_state, new_already_dropped)
  end

  def part2(input \\ IO.stream(:stdio, :line)) do
    indexed_bricks =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&to_brick/1)
      |> Enum.with_index()

    sorted_bricks =
      indexed_bricks |> Enum.sort_by(fn {{{_, _, z1}, {_, _, z2}}, _} -> min(z1, z2) end)

    point_to_bricks = generate_point_to_bricks(sorted_bricks)

    {after_indexed_bricks, after_point_to_brick} = propagate_fall(sorted_bricks, point_to_bricks)

    bricks_to_bricks_under =
      create_bricks_to_bricks_under(after_indexed_bricks, after_point_to_brick)

    0..(length(indexed_bricks) - 1)
    |> Enum.map(&count_fall(MapSet.new([&1]), bricks_to_bricks_under, MapSet.new()))
    |> Enum.sum()
  end
end
