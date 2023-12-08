defmodule AdventOfCode.Q8 do
  @node_start "AAA"
  @node_end "ZZZ"

  def parse_single_rule_pair(raw_rule) do
    [source | [tail]] = raw_rule |> String.split(" = (")

    [left, right] = tail |> String.slice(0..-2//1) |> String.split(", ")

    {source, {left, right}}
  end

  def parse_rules(raw_rules) do
    raw_rules
    |> Enum.map(&parse_single_rule_pair/1)
    |> Enum.into(%{})
  end

  def get_min_steps([], next_stream, rules, cur_node, target) do
    get_min_steps(Enum.reverse(next_stream), [], rules, cur_node, target)
  end

  def get_min_steps([cur_dir | tail], next_stream, rules, cur_node, target) do
    if cur_node == target do
      0
    else
      {left, right} = rules[cur_node]

      next_node =
        case cur_dir do
          "L" -> left
          "R" -> right
        end

      1 + get_min_steps(tail, [cur_dir | next_stream], rules, next_node, target)
    end
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    seq = input |> Enum.at(0) |> String.trim()
    raw_rules = input |> Enum.drop(2) |> Enum.map(&String.trim/1)
    rules = parse_rules(raw_rules)

    get_min_steps(String.graphemes(seq), [], rules, @node_start, @node_end)
  end

  def get_keys_containing(rules, letter) do
    rules
    |> Map.keys()
    |> Enum.filter(&String.contains?(&1, letter))
  end

  def time_to_first_z([], next_stream, rules, cur_node, z_set) do
    time_to_first_z(Enum.reverse(next_stream), [], rules, cur_node, z_set)
  end

  def time_to_first_z([cur_dir | tail], next_stream, rules, cur_node, z_set) do
    if MapSet.member?(z_set, cur_node) do
      0
    else
      {left, right} = rules[cur_node]

      next_node =
        case cur_dir do
          "L" -> left
          "R" -> right
        end

      1 + time_to_first_z(tail, [cur_dir | next_stream], rules, next_node, z_set)
    end
  end

  def gcd(a, b) do
    if b == 0 do
      a
    else
      gcd(b, rem(a, b))
    end
  end

  def lcm(a, b) do
    trunc(a * b / gcd(a, b))
  end

  def lcm(num_list) do
    num_list
    |> Enum.reduce(&lcm/2)
  end

  def part2(input \\ IO.stream(:stdio, :line)) do
    seq = input |> Enum.at(0) |> String.trim()
    raw_rules = input |> Enum.drop(2) |> Enum.map(&String.trim/1)
    rules = parse_rules(raw_rules)

    a_keys = get_keys_containing(rules, "A")
    z_keys = get_keys_containing(rules, "Z")
    z_set = MapSet.new(z_keys)

    a_keys
    |> Enum.map(&time_to_first_z(String.graphemes(seq), [], rules, &1, z_set))
    |> lcm
  end
end
