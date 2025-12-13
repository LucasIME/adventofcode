namespace Aoc2025

module Day12 =

    type Area = int64 * int64
    type Target = {
        Area: Area
        Shapes: int64[]
    }

    type Block = {
        id: int64
        shape: string
    }

    let parseRawBlock (rawBlock: string) =
        let idAndBlock = rawBlock.Split(":\n") 
        let id = int64 idAndBlock.[0]
        let block = idAndBlock.[1]
        { id = id; shape = block }

    let parseBlocks (rawBlocks: string[]) =
        rawBlocks
            |> Array.map parseRawBlock

    let parseTargetLine (line: string) =
        let split = line.Split(": ")

        let rawArea = split.[0]
        let areaParts = rawArea.Split("x")
        let areaLeft, areaRight = int64 areaParts.[0], int64 areaParts.[1]

        let rawShapes = split.[1].Split(" ")
        let shapes = rawShapes |> Array.map int64

        { 
            Area = (areaLeft, areaRight)
            Shapes = shapes
        }

    let parseRawTargets (rawTargets: string) =
        rawTargets.Split('\n') 
            |> Array.map parseTargetLine

    let parse (input: string) = 
        let blocks = input.Split("\n\n")
        let rawTargets = Array.last blocks

        parseBlocks blocks.[0..blocks.Length - 2], parseRawTargets rawTargets

    let canFit (blocks: Block array) (target: Target) =
        let fullArea = fst target.Area * snd target.Area
        let shapesRequired = target.Shapes |> Array.sum

        fullArea >= 9L * shapesRequired


    let part1 (input: string) =
        let shapes, targets = parse input

        targets
        |> Array.filter (canFit shapes)
        |> Array.length