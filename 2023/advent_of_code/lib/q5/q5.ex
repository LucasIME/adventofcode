defmodule AdventOfCode.Q5 do
  def reduce_rule_line(line, acc) do
    {name_to_range_map, cur_name} = acc

    cond do
      line == "" ->
        acc

      String.ends_with?(line, ":") ->
        new_name = String.split(line, "-") |> hd
        {name_to_range_map, new_name}

      true ->
        [dest_start, source_start, len] =
          line |> String.split(" ") |> Enum.map(&String.to_integer/1)

        dest_range = {dest_start, dest_start + len - 1}
        source_range = {source_start, source_start + len - 1}

        updated_range_set =
          MapSet.put(
            Map.get(name_to_range_map, cur_name, MapSet.new()),
            {source_range, dest_range}
          )

        updated_map = Map.put(name_to_range_map, cur_name, updated_range_set)
        {updated_map, cur_name}
    end
  end

  def parse_rules(input) do
    Enum.reduce(input, {%{}, ""}, &reduce_rule_line/2)
  end

  def parse_input(input) do
    ["seeds: " <> raw_seeds] = Enum.take(input, 1)

    seeds =
      raw_seeds
      |> String.trim()
      |> String.split(" ")
      |> Enum.map(&String.to_integer/1)

    {rules, _last_processed_name} = parse_rules(Enum.drop(input, 2))

    {seeds, rules}
  end

  def value_in_range?({range_start, range_end}, value) do
    value >= range_start and value <= range_end
  end

  def get_corresponding_in_range({source_range, dest_range}, value) do
    {source_start, _source_end} = source_range
    {dest_start, _dest_end} = dest_range
    offset = value - source_start
    dest_start + offset
  end

  def apply_rule(value, key, rules) do
    ranges = rules[key]
    default_single_element_range = {{value, value}, {value, value}}

    ranges
    |> Enum.find(default_single_element_range, fn {source_range, _dest_range} ->
      value_in_range?(source_range, value)
    end)
    |> get_corresponding_in_range(value)
  end

  def seed_to_location(seed, rules) do
    seed
    |> apply_rule("seed", rules)
    |> apply_rule("soil", rules)
    |> apply_rule("fertilizer", rules)
    |> apply_rule("water", rules)
    |> apply_rule("light", rules)
    |> apply_rule("temperature", rules)
    |> apply_rule("humidity", rules)
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    {seeds, rules} = input |> Enum.map(&String.trim/1) |> parse_input()

    seeds
    |> Enum.map(fn seed -> seed_to_location(seed, rules) end)
    |> Enum.min()
  end
end
