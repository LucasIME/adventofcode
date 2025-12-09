namespace Aoc2025.Tests

open Xunit
open Aoc2025

module Day09Tests =

    [<Fact>]
    let ``Part 1 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day09/ex1.txt")
        let result = Day09.part1 input
        Assert.Equal(50L, result)

    [<Fact>]
    let ``Part 1 Test`` () =
        let input = System.IO.File.ReadAllText("resources/day09/input.txt")
        let result = Day09.part1 input
        Assert.Equal( 4744899849L, result)

    [<Fact>]
    let ``Part 2 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day09/ex1.txt")
        let result = Day09.part2 input
        Assert.Equal(24L, result)