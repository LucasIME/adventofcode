namespace Aoc2025
open System

module Day06 =

    type Input = int64 list list * string list

    let transpose (matrix: int64 list list) : int64 list list =
        let rec transposeHelper (matrix: int64 list list) (acc: int64 list list) : int64 list list =
            match matrix with
            | [] -> acc
            | [] :: _ -> acc
            | _ ->
                let heads = matrix |> List.map List.head
                let tails = matrix |> List.map List.tail
                transposeHelper tails (acc @ [heads])
        transposeHelper matrix []

    let parse (input: string) : Input =
        let rawLines = input.Split('\n')
        let rawOps = Array.last rawLines
        let rawNums = rawLines |> Array.take (rawLines.Length - 1)

        let nums = 
            rawNums
            |> Array.map (fun line -> 
                line.Split(' ', StringSplitOptions.RemoveEmptyEntries)
                |> Array.map int64
                |> Array.toList
            )
            |> Array.toList
        
        let operators =
            rawOps.Split(' ', StringSplitOptions.RemoveEmptyEntries)

        transpose nums, operators |> Array.toList

    let reduceWithOp (nums: int64 list) (op: string) : int64 =
        match op with
        | "+" -> List.sum nums
        | "*" -> List.fold (*) 1L nums
        | _ -> failwithf "Unknown operator: %s" op

    let solve (arrays: int64 list list) (operators: string list) : int64 =
        let reducedArrays = 
            List.map2 reduceWithOp arrays operators
        List.reduce (+) reducedArrays

    let part1 (input: string) =
        let (arrays, operators) = parse input
        solve arrays operators

    let rawParse (input: string) : (char array array) * string =
        let rawLines = input.Split('\n')
        let rawOps = Array.last rawLines
        let rawNums = 
            rawLines 
            |> Array.take (rawLines.Length - 1)
            |> Array.map (fun line -> line.ToCharArray())

        rawNums, rawOps

    let getOpsClusters (rawOps: string) : (char * int * int) list =
        let indices =
            rawOps
            |> Seq.indexed
            |> Seq.filter (fun (_, c) -> c <> ' ')
            |> Seq.toList

        let nextIndices = 
            indices
            |> Seq.skip 1
            |> Seq.toList

        let realNextInidices = nextIndices @ [ (rawOps.Length, ' ') ]

        let charAndRange =
            indices
            |> Seq.map2 (fun (nextI, _) (i, c) -> 
                (c, i, nextI)) realNextInidices

        charAndRange |> Seq.toList

    let getColNum (rawNums: char array array) (colIdx: int) : int64 =
        let rows = rawNums.Length
        let lastValidRow = 
            rawNums 
            |> Array.map (fun row -> row.[colIdx])
            |> Array.tryFindIndexBack(fun c -> c <> ' ')
            |> Option.defaultValue -1

        let colNums = 
            [ 0 .. lastValidRow ]
            |> List.map (fun rowIdx ->
                let digit = match rawNums.[rowIdx].[colIdx] with
                            | ' ' -> 0L
                            | c when Char.IsDigit c -> int64 (c - '0')
                            | _ -> failwith "Invalid digit"
                let tenPower = pown 10L (lastValidRow - rowIdx)
                digit * tenPower
            )

        colNums |> List.sum

    let solveCluster (rawNums: char array array) (op: char, startIdx: int, endIdx: int) : int64 =
        let colNums = 
            [ startIdx .. endIdx - 1 ]
            |> List.map (fun colIdx -> getColNum rawNums colIdx)
            |> List.filter (fun n -> n <> 0L)

        colNums
        |> List.reduce (match op with
                        | '+' -> (+)
                        | '*' -> (*)
                        | _ -> failwith "Invalid operator")

    let solve2 (rawNums: char array array) (opsClusters: (char * int * int) list) : int64 =
        let clusterNums = 
            opsClusters
            |> List.map (fun cluster -> solveCluster rawNums cluster)
        
        clusterNums |> List.sum

    let part2 (input: string) =
        let (rawNums, rawOPs) = rawParse input
        let opsClusters = getOpsClusters rawOPs

        solve2 rawNums opsClusters