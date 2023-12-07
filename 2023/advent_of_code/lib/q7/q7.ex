defmodule AdventOfCode.Q7 do
  def to_hand_and_bid(line) do
    [raw_hand, raw_bid] = String.split(line, " ")
    {raw_hand, String.to_integer(raw_bid)}
  end

  def card_freq_to_points(card_freq) do
    freq_values = card_freq |> Map.values() |> Enum.sort() |> Enum.reverse()

    case freq_values do
      [5] -> 7
      [4, 1] -> 6
      [3, 2] -> 5
      [3, 1, 1] -> 4
      [2, 2, 1] -> 3
      [2, 1, 1, 1] -> 2
      _ -> 1
    end
  end

  @card_power_order ~w(A K Q J T 9 8 7 6 5 4 3 2)
  @card_to_power @card_power_order
                 |> Enum.reverse()
                 |> Enum.with_index()
                 |> Enum.into(%{})

  def card_to_val(card) do
    @card_to_power[card]
  end

  def hand_to_card_power_array(hand) do
    hand
    |> String.graphemes()
    |> Enum.map(&card_to_val/1)
  end

  def compare_hands(hand1, hand2) do
    hand1_freq = hand1 |> String.graphemes() |> Enum.frequencies()
    hand1_points = card_freq_to_points(hand1_freq)
    hand_array = hand_to_card_power_array(hand1)

    hand2_freq = hand2 |> String.graphemes() |> Enum.frequencies()
    hand2_points = card_freq_to_points(hand2_freq)
    hand2_array = hand_to_card_power_array(hand2)

    {hand1_points, hand_array} < {hand2_points, hand2_array}
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    input
    |> Enum.map(&String.trim/1)
    |> Enum.map(&to_hand_and_bid/1)
    |> Enum.sort(fn {hand1, _bid1}, {hand2, _bid2} -> compare_hands(hand1, hand2) end)
    |> Enum.with_index(1)
    |> Enum.map(fn {{_hand, bid}, index} -> bid * index end)
    |> Enum.sum()
  end
end
