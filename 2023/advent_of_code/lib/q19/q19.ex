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
end
