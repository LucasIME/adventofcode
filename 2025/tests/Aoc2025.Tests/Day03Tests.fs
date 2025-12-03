namespace Aoc2025.Tests

open Xunit
open Aoc2025

module Day03Tests =

    [<Fact>]
    let ``Part 1 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day03/ex1.txt")
        let result = Day03.part1 input
        Assert.Equal(357, result)

    [<Fact>]
    let ``Part 1 Test`` () =
        let input = System.IO.File.ReadAllText("resources/day03/input.txt")
        let result = Day03.part1 input
        Assert.Equal(17229, result)
