namespace Aoc2025.Tests

open Xunit
open Aoc2025

module Day03Tests =

    [<Fact>]
    let ``Part 1 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day03/ex1.txt")
        let result = Day03.part1 input
        Assert.Equal(357L, result)

    [<Fact>]
    let ``Part 1 Test`` () =
        let input = System.IO.File.ReadAllText("resources/day03/input.txt")
        let result = Day03.part1 input
        Assert.Equal(17229L, result)

    [<Fact>]
    let ``Part 2 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day03/ex1.txt")
        let result = Day03.part2 input
        Assert.Equal(3121910778619L, result)

    [<Fact>]
    let ``Part 2 Test`` () =
        let input = System.IO.File.ReadAllText("resources/day03/input.txt")
        let result = Day03.part2 input
        Assert.Equal(170520923035051L, result)
