namespace Aoc2025.Tests

open Xunit
open Aoc2025

module Day10Tests =

    [<Fact>]
    let ``Part 1 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day10/ex1.txt")
        let result = Day10.part1 input
        Assert.Equal(7L, result)

    [<Fact>]
    let ``Part 1 Test`` () =
        let input = System.IO.File.ReadAllText("resources/day10/input.txt")
        let result = Day10.part1 input
        Assert.Equal(396L, result)

    [<Fact>]
    let ``Part 2 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day10/ex1.txt")
        let result = Day10.part2 input
        Assert.Equal(33L, result)

    [<Fact>]
    let ``Part 2 Test`` () =
        let input = System.IO.File.ReadAllText("resources/day10/input.txt")
        let result = Day10.part2 input
        Assert.Equal(-1L, result)