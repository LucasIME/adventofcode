namespace Aoc2025.Tests

open Xunit
open Aoc2025

module Day07Tests =

    [<Fact>]
    let ``Part 1 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day07/ex1.txt")
        let result = Day07.part1 input
        Assert.Equal(21, result)

    [<Fact>]
    let ``Part 1 Test`` () =
        let input = System.IO.File.ReadAllText("resources/day07/input.txt")
        let result = Day07.part1 input
        Assert.Equal(1613, result)

    [<Fact>]
    let ``Part 2 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day07/ex1.txt")
        let result = Day07.part2 input
        Assert.Equal(40L, result)

    [<Fact>]
    let ``Part 2 Test`` () =
        let input = System.IO.File.ReadAllText("resources/day07/input.txt")
        let result = Day07.part2 input
        Assert.Equal(48021610271997L, result)