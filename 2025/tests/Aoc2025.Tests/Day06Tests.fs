namespace Aoc2025.Tests

open Xunit
open Aoc2025

module Day06Tests =

    [<Fact>]
    let ``Part 1 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day06/ex1.txt")
        let result = Day06.part1 input
        Assert.Equal(4277556L, result)

    [<Fact>]
    let ``Part 1 Test`` () =
        let input = System.IO.File.ReadAllText("resources/day06/input.txt")
        let result = Day06.part1 input
        Assert.Equal(6605396225322L, result)