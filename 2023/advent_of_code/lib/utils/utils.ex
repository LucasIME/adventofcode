defmodule AdventOfCode.Utils do
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
end
