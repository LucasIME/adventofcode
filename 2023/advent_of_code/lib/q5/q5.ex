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

  def ranges_overlap?({range1_start, range1_end}, {range2_start, range2_end}) do
    max(range1_end, range2_end) - min(range1_start, range2_start) <=
      range1_end - range1_start + (range2_end - range2_start)
  end

  def split_range2({rule_src_range, rule_dest_range}, {seed_start, seed_end}) do
    {src_start, src_end} = rule_src_range

    result =
      if src_start <= seed_start do
        if src_end >= seed_end do
          # src_start, [seed_start, seed_end], src_end
          new_start = get_corresponding_in_range({rule_src_range, rule_dest_range}, seed_start)
          new_end = get_corresponding_in_range({rule_src_range, rule_dest_range}, seed_end)
          {[], {new_start, new_end}}
        else
          # src_start, [seed_start, src_end], seed_end
          new_start = get_corresponding_in_range({rule_src_range, rule_dest_range}, seed_start)
          new_end = get_corresponding_in_range({rule_src_range, rule_dest_range}, src_end)
          # {[{src_end, seed_end}], {new_start, new_end}}
          {[{src_end + 1, seed_end}], {new_start, new_end}}
        end
      else
        if src_end >= seed_end do
          # seed_start, [src_start, seed_end], src_end
          conversion_start =
            get_corresponding_in_range({rule_src_range, rule_dest_range}, src_start)

          conversion_end = get_corresponding_in_range({rule_src_range, rule_dest_range}, seed_end)

          {[{seed_start, src_start - 1}], {conversion_start, conversion_end}}
        else
          # seed_start, [src_start, src_end], seed_end
          conversion_start =
            get_corresponding_in_range({rule_src_range, rule_dest_range}, src_start)

          conversion_end = get_corresponding_in_range({rule_src_range, rule_dest_range}, src_end)

          {
            [{seed_start, src_start - 1}, {src_end + 1, seed_end}],
            {conversion_start, conversion_end}
          }
        end
      end

    result
  end

  def inner_apply_range_rule(_raw_seeds, raw_seed_range_set, [], out) do
    MapSet.to_list(raw_seed_range_set) ++ out
  end

  def inner_apply_range_rule([], raw_seed_range_set, [_rule | rule_tail], out) do
    inner_apply_range_rule(MapSet.to_list(raw_seed_range_set), raw_seed_range_set, rule_tail, out)
  end

  def inner_apply_range_rule(
        [raw_seed_range | seed_tail],
        raw_seed_range_set,
        [rule | rule_tail],
        out
      ) do
    {src_range, _dest_range} = rule

    if ranges_overlap?(src_range, raw_seed_range) do
      {new_raw, new_out} = split_range2(rule, raw_seed_range)
      new_seed_set = MapSet.delete(raw_seed_range_set, raw_seed_range)

      new_seed_set =
        Enum.reduce(new_raw, new_seed_set, fn raw_range, acc_set ->
          MapSet.put(acc_set, raw_range)
        end)

      inner_apply_range_rule(new_raw ++ seed_tail, new_seed_set, [rule | rule_tail], [
        new_out | out
      ])
    else
      inner_apply_range_rule(seed_tail, raw_seed_range_set, [rule | rule_tail], out)
    end
  end

  def apply_range_rule(seed_range, key, rules) do
    rule_ranges = rules[key]

    inner_apply_range_rule(
      [seed_range],
      MapSet.new([seed_range]),
      MapSet.to_list(rule_ranges),
      []
    )
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

  def seed_range_to_location(seed_range, rules) do
    [seed_range]
    |> Enum.flat_map(fn range -> apply_range_rule(range, "seed", rules) end)
    |> Enum.flat_map(fn range -> apply_range_rule(range, "soil", rules) end)
    |> Enum.flat_map(fn range -> apply_range_rule(range, "fertilizer", rules) end)
    |> Enum.flat_map(fn range -> apply_range_rule(range, "water", rules) end)
    |> Enum.flat_map(fn range -> apply_range_rule(range, "light", rules) end)
    |> Enum.flat_map(fn range -> apply_range_rule(range, "temperature", rules) end)
    |> Enum.flat_map(fn range -> apply_range_rule(range, "humidity", rules) end)
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    {seeds, rules} = input |> Enum.map(&String.trim/1) |> parse_input()

    seeds
    |> Enum.map(fn seed -> seed_to_location(seed, rules) end)
    |> Enum.min()
  end

  def parse_input2(input) do
    ["seeds: " <> raw_seeds] = Enum.take(input, 1)

    seed_ranges =
      raw_seeds
      |> String.split(" ")
      |> Enum.map(&String.to_integer/1)
      |> Enum.chunk_every(2)
      |> Enum.map(fn [start, range_len] -> {start, start + range_len - 1} end)

    {rules, _last_processed_name} = parse_rules(Enum.drop(input, 2))

    {seed_ranges, rules}
  end

  def part2(input \\ IO.stream(:stdio, :line)) do
    {seed_ranges, rules} = input |> Enum.map(&String.trim/1) |> parse_input2()

    location_ranges =
      seed_ranges
      |> Enum.flat_map(fn seed -> seed_range_to_location(seed, rules) end)

    location_ranges
    |> Enum.map(fn {start, _r_end} -> start end)
    |> Enum.min()
  end
end
