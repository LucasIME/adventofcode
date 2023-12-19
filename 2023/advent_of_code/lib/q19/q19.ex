defmodule AdventOfCode.Q19 do
  def split_by_empty_line(input) do
    input
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
  end

  def parse_rule(raw_rule) do
    [rule_name | tail_with_trailing_parens] = String.split(raw_rule, "{")
    raw_mapping_tail = tail_with_trailing_parens |> hd |> String.trim_trailing("}")
    raw_mapping_list = String.split(raw_mapping_tail, ",")

    mapping_list =
      raw_mapping_list
      |> Enum.map(fn raw_map -> String.split(raw_map, ":") |> List.to_tuple() end)

    {rule_name, mapping_list}
  end

  def parse_rules(raw_rules) do
    raw_rules |> Enum.map(&parse_rule/1) |> Enum.into(%{})
  end

  def parse_point(raw_point) do
    raw_point
    |> String.trim_trailing("}")
    |> String.trim_leading("{")
    |> String.split(",")
    |> Enum.map(fn raw_equals ->
      [name, value] = String.split(raw_equals, "=")
      {name, String.to_integer(value)}
    end)
    |> Enum.into(%{})
  end

  def parse_points(raw_points) do
    raw_points |> Enum.map(&parse_point/1)
  end

  def process_op(val1, op, val2) do
    case op do
      ">" -> val1 > val2
      "<" -> val1 < val2
    end
  end

  def matches_guard?(point, guard) do
    key = String.at(guard, 0)
    operator = String.at(guard, 1)
    value = String.slice(guard, 2..-1//1) |> String.to_integer()

    Map.get(point, key) |> process_op(operator, value)
  end

  def matches_rule?(point, [last_guard], rules) do
    case last_guard do
      {"A"} -> true
      {"R"} -> false
      {other_rule} -> matches_rule?(point, Map.get(rules, other_rule), rules)
    end
  end

  def matches_rule?(point, [{guard, target} | guard_tail], rules) do
    if matches_guard?(point, guard) do
      case target do
        "A" -> true
        "R" -> false
        other_rule -> matches_rule?(point, rules[other_rule], rules)
      end
    else
      matches_rule?(point, guard_tail, rules)
    end
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    [raw_rules, raw_points] =
      input
      |> Enum.map(&String.trim/1)
      |> split_by_empty_line()

    rules = raw_rules |> parse_rules()
    points = raw_points |> parse_points()

    matched_points =
      points
      |> Enum.filter(&matches_rule?(&1, Map.get(rules, "in"), rules))

    matched_points
    |> Enum.flat_map(&Map.values/1)
    |> Enum.sum()
  end

  @default_range 1..4000
  @all_match %{
    "x" => [@default_range],
    "m" => [@default_range],
    "a" => [@default_range],
    "s" => [@default_range]
  }

  def get_exclude_guard(guard) do
    key = String.at(guard, 0)
    operator = String.at(guard, 1)
    value = String.slice(guard, 2..-1//1) |> String.to_integer()

    case operator do
      ">" -> %{key => [(value + 1)..4000]}
      "<" -> %{key => [1..(value - 1)]}
    end
  end

  def get_guard_range(guard) do
    key = String.at(guard, 0)
    operator = String.at(guard, 1)
    value = String.slice(guard, 2..-1//1) |> String.to_integer()

    new_key =
      case operator do
        ">" -> %{key => [(value + 1)..4000]}
        "<" -> %{key => [1..(value - 1)]}
      end

    Map.merge(@all_match, new_key)
  end

  def merge_ranges(ranges) do
    sorted_ranges = Enum.sort(ranges, fn a, b -> a.first < b.first end)

    Enum.reduce(sorted_ranges, [], fn current_range, acc ->
      case acc do
        [] ->
          [current_range]

        [previous_range | tail] ->
          if current_range.first - 1 <= previous_range.last do
            [previous_range.first..max(previous_range.last, current_range.last) | tail]
          else
            [current_range | acc]
          end
      end
    end)
    |> Enum.reverse()
  end

  def union_key(key, m1, m2) do
    ranges1 = Map.get(m1, key, [])
    ranges2 = Map.get(m2, key, [])
    merge_ranges(ranges1 ++ ranges2)
  end

  def union(intervals1, intervals2) do
    keys = ["x", "m", "a", "s"]

    keys
    |> Enum.map(fn key -> {key, union_key(key, intervals1, intervals2)} end)
    |> Enum.into(%{})
  end

  def intersect_ranges(range_list1, range_list2) do
    range_list1
    |> Enum.flat_map(fn r1 ->
      Enum.map(range_list2, fn r2 -> find_intersection(r1, r2) end)
    end)
    |> Enum.filter(& &1)
  end

  defp find_intersection(%Range{first: a, last: b}, %Range{first: x, last: y}) do
    if y < a || x > b do
      nil
    else
      first = max(a, x)
      last = min(b, y)
      first..last
    end
  end

  def inter_key(key, m1, m2) do
    ranges1 = Map.get(m1, key, [])
    ranges2 = Map.get(m2, key, [])
    intersect_ranges(ranges1, ranges2)
  end

  def intersection(intervals1, intervals2) do
    keys = ["x", "m", "a", "s"]

    keys
    |> Enum.map(fn key -> {key, inter_key(key, intervals1, intervals2)} end)
    |> Enum.into(%{})
  end

  def matching_intervals(
        rule,
        rules,
        excluding_intervals \\ %{"x" => [], "m" => [], "a" => [], "s" => []}
      )

  def matching_intervals([{guard, target} | rule_tail], rules, excluding_intervals) do
    guard_range = get_guard_range(guard)

    matching_points =
      case target do
        "A" ->
          [guard_range]

        "R" ->
          []

        other_rule ->
          other_rule_match = matching_intervals(rules[other_rule], rules, excluding_intervals)
          other_rule_match |> Enum.map(&intersection(guard_range, &1))
      end

    my_match = matching_points |> Enum.map(fn point -> difference(point, excluding_intervals) end)

    new_exclude = union(get_exclude_guard(guard), excluding_intervals)
    my_match ++ matching_intervals(rule_tail, rules, new_exclude)
  end

  def matching_intervals([last_guard], rules, excluding_intervals) do
    case last_guard do
      {"A"} -> [difference(@all_match, excluding_intervals)]
      {"R"} -> []
      {other_rule} -> matching_intervals(rules[other_rule], rules, excluding_intervals)
    end
  end

  def difference(intervals1, intervals2) do
    keys = ["x", "m", "a", "s"]

    keys
    |> Enum.map(fn key -> {key, diff_key(key, intervals1, intervals2)} end)
    |> Enum.into(%{})
  end

  def diff_key(key, m1, m2) do
    ranges1 = Map.get(m1, key, [])
    ranges2 = Map.get(m2, key, [])
    difference_ranges(ranges1, ranges2)
  end

  def difference_ranges(range_list1, range_list2) do
    range_list1
    |> Enum.flat_map(fn r1 ->
      Enum.reduce(range_list2, [r1], fn r2, acc ->
        Enum.flat_map(acc, &subtract_ranges(&1, r2))
      end)
    end)
  end

  defp subtract_ranges(%Range{first: a, last: b}, %Range{first: x, last: y}) do
    cond do
      y < a || x > b ->
        [%Range{first: a, last: b, step: 1}]

      x <= a && y >= b ->
        []

      x <= a && y < b ->
        [Range.new(y + 1, b)]

      x > a && y >= b ->
        [Range.new(a, x - 1)]

      x > a && y < b ->
        [Range.new(a, x - 1), Range.new(y + 1, b)]
    end
  end

  def interval_total([]) do
    0
  end

  def interval_total(interval) do
    interval
    |> Map.values()
    |> Enum.map(fn [range] -> Range.size(range) end)
    |> Enum.product()
  end

  def part2(input \\ IO.stream(:stdio, :line)) do
    [raw_rules | _] =
      input
      |> Enum.map(&String.trim/1)
      |> split_by_empty_line()

    rules = raw_rules |> parse_rules()

    matching_intervals(Map.get(rules, "in"), rules)
    |> Enum.map(&interval_total/1)
    |> Enum.sum()
  end
end
