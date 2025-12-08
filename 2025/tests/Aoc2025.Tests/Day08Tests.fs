namespace Aoc2025.Tests

open Xunit
open Aoc2025

module Day08Tests =

    [<Fact>]
    let ``Part 1 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day08/ex1.txt")
        let result = Day08.part1 input 10
        Assert.Equal(40, result)

    [<Fact>]
    let ``Part 1 Test`` () =
        let input = System.IO.File.ReadAllText("resources/day08/input.txt")
        let result = Day08.part1 input 1000
        Assert.Equal( 117000, result)

    [<Fact>]
    let ``Part 2 Example Test`` () =
        let input = System.IO.File.ReadAllText("resources/day08/ex1.txt")
        let result = Day08.part2 input 
        Assert.Equal(25272L, result)

    [<Fact>]
    let ``Part 2 Test`` () =
        let input = System.IO.File.ReadAllText("resources/day08/input.txt")
        let result = Day08.part2 input
        Assert.Equal( 8368033065L, result)