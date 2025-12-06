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