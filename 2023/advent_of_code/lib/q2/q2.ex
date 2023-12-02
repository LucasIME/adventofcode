defmodule AdventOfCode.Q2 do
  def lineToGame(line) do
    ["Game " <> number | tail] = String.split(line, ": ")
    {number!(number), getMaxBalls(hd(tail))}
  end

  def getMaxBalls(line) do
    plays = String.split(line, "; ")

    plays
    |> Enum.map(&playToBallsMap/1)
  end

  def playToBallsMap(play) do
    singleBallList = String.split(play, ", ")

    singleBallList
    |> Enum.map(&ballToMap/1)
    |> Enum.reduce(&Map.merge/2)
  end

  def ballToMap(ball) do
    [rawNum, color] = String.split(ball, " ")
    %{color => number!(rawNum)}
  end

  def number!(string) do
    case Integer.parse(string) do
      {num, _} ->
        num

      :error ->
        raise "Error parsing number"
    end
  end

  def gameToMaxBalls({gameNum, plays}) do
    maxBalls =
      plays
      |> Enum.reduce(fn map1, map2 ->
        Map.merge(map1, map2, fn _key, v1, v2 -> max(v1, v2) end)
      end)

    {gameNum, maxBalls}
  end

  def isValidGame({_gameNum, maxPerBall}) do
    Map.get(maxPerBall, "red", 0) <= 12 and
      Map.get(maxPerBall, "green", 0) <= 13 and
      Map.get(maxPerBall, "blue", 0) <= 14
  end

  def part1(input \\ IO.stream(:stdio, :line)) do
    input
    |> Enum.map(&String.trim/1)
    |> Enum.map(&lineToGame/1)
    |> Enum.map(&gameToMaxBalls/1)
    |> Enum.filter(&isValidGame/1)
    |> Enum.map(fn {gameNum, _} -> gameNum end)
    |> Enum.sum()
  end

  def toGamePower({_gameNum, maxPerBall}) do
    Enum.reduce(Map.values(maxPerBall), &*/2)
  end

  def part2(input \\ IO.stream(:stdio, :line)) do
    input
    |> Enum.map(&String.trim/1)
    |> Enum.map(&lineToGame/1)
    |> Enum.map(&gameToMaxBalls/1)
    |> Enum.map(&toGamePower/1)
    |> Enum.sum()
  end
end
