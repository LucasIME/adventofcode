namespace Aoc2025.Tests

open Xunit
open Aoc2025

module Day02Tests =

    [<Fact>]
    let ``Part 1 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day02/ex1.txt")
        let result = Day02.part1 input
        Assert.Equal(1227775554L, result)

    [<Fact>]
    let ``Part 1 Test`` () =
        let input = System.IO.File.ReadAllText("resources/day02/input.txt")
        let result = Day02.part1 input
        Assert.Equal(22062284697L, result)

    [<Fact>]
    let ``Part 2 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day02/ex1.txt")
        let result = Day02.part2 input
        Assert.Equal(4174379265L, result)

    [<Fact>]
    let ``Part 2 Test`` () =
        let input = System.IO.File.ReadAllText("resources/day02/input.txt")
        let result = Day02.part2 input
        Assert.Equal(46666175279L, result)
