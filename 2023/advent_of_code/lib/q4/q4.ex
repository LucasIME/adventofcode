defmodule AdventOfCode.Q4 do
  def get_scratch_point(winner_set, hand) do
    Enum.reduce(hand, 0, fn card, points ->
      case card in winner_set do
        false ->
          points

        true ->
          case points do
            0 -> 1
            x -> x * 2
          end
      end
    end)
  end

  def to_winning_cards_and_hand(string) do
    ["Card " <> n | [remaining]] = String.split(string, ": ")

    [raw_winners, raw_hand] = String.split(remaining, " | ", trim: true)

    winners =
      String.split(raw_winners, " ", trim: true)
      |> Enum.map(&String.to_integer/1)
      |> MapSet.new()

    hand =
      String.split(raw_hand, " ", trim: true)
      |> Enum.map(&String.to_integer/1)

    {n |> String.trim() |> String.to_integer(), winners, hand}
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    input
    |> Enum.map(&String.trim/1)
    |> Enum.map(&to_winning_cards_and_hand/1)
    |> Enum.map(fn {_n, winners, hand} -> get_scratch_point(winners, hand) end)
    |> Enum.sum()
  end

  def add_cards_in_range(frequency_map, range, times) do
    Enum.reduce(range, frequency_map, fn target_card, acc_freq_map ->
      Map.put(acc_freq_map, target_card, Map.get(acc_freq_map, target_card) + times)
    end)
  end

  def get_scratch_card_count(winner_set, hand) do
    Enum.reduce(hand, 0, fn card, points ->
      case card in winner_set do
        false ->
          points

        true ->
          points + 1
      end
    end)
  end

  def part2(input \\ IO.stream(:stdio, :line)) do
    all_cards =
      input
      |> Enum.map(&String.trim/1)
      |> Enum.map(&to_winning_cards_and_hand/1)

    count_per_card = 1..length(all_cards) |> Enum.frequencies()

    count_per_card =
      Enum.reduce(all_cards, count_per_card, fn {n, winners, hand}, acc_freq ->
        card_num_to_gen = get_scratch_card_count(winners, hand)

        if card_num_to_gen == 0 do
          acc_freq
        else
          add_cards_in_range(acc_freq, (n + 1)..(n + card_num_to_gen), Map.get(acc_freq, n))
        end
      end)

    count_per_card
    |> Map.values()
    |> Enum.sum()
  end
end
