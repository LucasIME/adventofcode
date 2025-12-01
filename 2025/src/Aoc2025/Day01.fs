namespace Aoc2025

module Day01 =
    type RotationDirection =
        | Right
        | Left 

    type Command = {
        Direction: RotationDirection
        Steps: int
    }

    let parseLine(line: string) : Command =
        let dir =
            match line.[0] with
            | 'R' -> Right
            | 'L' -> Left
            | _ -> failwith "Invalid direction"
        let steps = line.[1..] |> int
        { Direction = dir; Steps = steps }

    let rec processSteps(commands: Command list, curPos: int, zeroNums: int) : int =
        match commands with
        | [] -> zeroNums
        | head :: tail ->
            let mul = match head.Direction with
                      | Right -> 1
                      | Left -> -1
            let newPos = ((curPos + mul * head.Steps) + 100) % 100
            let newZeroNums = if newPos = 0 then zeroNums + 1 else zeroNums
            processSteps(tail, newPos, newZeroNums)

    let part1 (input: string) =
        let lines = input.Split('\n')
        let parsedLines = lines |> Array.map parseLine
        processSteps(List.ofArray parsedLines, 50, 0)