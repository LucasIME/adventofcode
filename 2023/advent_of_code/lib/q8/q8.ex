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
end
