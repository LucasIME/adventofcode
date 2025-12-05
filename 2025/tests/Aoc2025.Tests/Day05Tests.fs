namespace Aoc2025.Tests

open Xunit
open Aoc2025

module Day05Tests =

    [<Fact>]
    let ``Part 1 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day05/ex1.txt")
        let result = Day05.part1 input
        Assert.Equal(3, result)

    [<Fact>]
    let ``Part 1 Test`` () =
        let input = System.IO.File.ReadAllText("resources/day05/input.txt")
        let result = Day05.part1 input
        Assert.Equal(775, result)

    [<Fact>]
    let ``Part 2 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day05/ex1.txt")
        let result = Day05.part2 input
        Assert.Equal(14L, result)

    [<Fact>]
    let ``Part 2 Test`` () =
        let input = System.IO.File.ReadAllText("resources/day05/input.txt")
        let result = Day05.part2 input
        Assert.Equal(350684792662845L, result)
