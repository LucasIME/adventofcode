namespace Aoc2025.Tests

open Xunit
open Aoc2025

module Day01Tests =

    [<Fact>]
    let ``Part 1 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day01/ex1.txt")
        let result = Day01.part1 input
        Assert.Equal(3, result)

    [<Fact>]
    let ``Part 1 Test`` () =
        let input = System.IO.File.ReadAllText("resources/day01/input.txt")
        let result = Day01.part1 input
        Assert.Equal(1165, result)