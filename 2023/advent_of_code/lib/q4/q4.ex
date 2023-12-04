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
    ["Card " <> _n | [remaining]] = String.split(string, ": ")

    [raw_winners, raw_hand] = String.split(remaining, " | ", trim: true)

    winners =
      String.split(raw_winners, " ", trim: true)
      |> Enum.map(&String.to_integer/1)
      |> MapSet.new()

    hand =
      String.split(raw_hand, " ", trim: true)
      |> Enum.map(&String.to_integer/1)

    {winners, hand}
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    input
    |> Enum.map(&String.trim/1)
    |> Enum.map(&to_winning_cards_and_hand/1)
    |> Enum.map(fn {winners, hand} -> get_scratch_point(winners, hand) end)
    |> Enum.sum()
  end
end
