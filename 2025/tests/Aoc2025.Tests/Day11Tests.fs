namespace Aoc2025.Tests

open Xunit
open Aoc2025

module Day11Tests =

    [<Fact>]
    let ``Part 1 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day11/ex1.txt")
        let result = Day11.part1 input
        Assert.Equal(5L, result)

    [<Fact>]
    let ``Part 1 Test`` () =
        let input = System.IO.File.ReadAllText("resources/day11/input.txt")
        let result = Day11.part1 input
        Assert.Equal( 585L, result)

    [<Fact>]
    let ``Part 2 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day11/ex2.txt")
        let result = Day11.part2 input
        Assert.Equal(2L, result)

    [<Fact>]
    let ``Part 2 Test`` () =
        let input = System.IO.File.ReadAllText("resources/day11/input.txt")
        let result = Day11.part2 input
        Assert.Equal( -1L, result)