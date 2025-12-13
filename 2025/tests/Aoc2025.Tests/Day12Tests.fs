namespace Aoc2025.Tests

open Xunit
open Aoc2025

module Day12Tests =
    let ``Part 1 Test`` () =
        let input = System.IO.File.ReadAllText("resources/day12/input.txt")
        let result = Day12.part1 input
        Assert.Equal(433L, result)
