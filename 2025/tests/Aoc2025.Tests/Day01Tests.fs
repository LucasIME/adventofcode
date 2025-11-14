namespace Aoc2025.Tests

open Xunit
open Aoc2025

module Day01Tests =

    [<Fact>]
    let ``Part 1 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day01.txt")
        let result = Day01.part1 input
        Assert.Equal(4, result)