namespace Aoc2025.Tests

open Xunit
open Aoc2025

module Day04Tests =

    [<Fact>]
    let ``Part 1 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day04/ex1.txt")
        let result = Day04.part1 input
        Assert.Equal(13, result)

    [<Fact>]
    let ``Part 1 Test`` () =
        let input = System.IO.File.ReadAllText("resources/day04/input.txt")
        let result = Day04.part1 input
        Assert.Equal(1502, result)

    [<Fact>]
    let ``Part 2 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day04/ex1.txt")
        let result = Day04.part2  input
        Assert.Equal(43, result)

    [<Fact>]
    let ``Part 2 Test`` () =
        let input = System.IO.File.ReadAllText("resources/day04/input.txt")
        let result = Day04.part2 input
        Assert.Equal(9083, result)
