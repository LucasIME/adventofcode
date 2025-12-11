namespace Aoc2025

open System.Collections.Generic

module Day11 =
    let parseLine (line: string) : (string * string) list =
        let keySplit = line.Split(": ")
        let key = keySplit.[0]
        let values = keySplit.[1].Split(" ")

        values |> Array.map (fun v -> (key, v)) |> Array.toList
    let parseGraph (input: string) = 
        let lines = input.Split('\n')
    
        let graph = 
            lines
                |> Array.toList
                |> List.collect parseLine
                |> List.groupBy fst
                |> List.map (fun (k, v) -> (k, v |> List.map snd))
                |> Map.ofList
        
        graph
    
    let rec countPaths (graph: Map<string, string list>) (start: string) (target: string) : int64 =
        if start = target then
            1L
        else
            let neighs = graph |> Map.tryFind start |> Option.defaultValue []
            let result = 
                neighs 
                |> List.sumBy (fun neighbor -> 
                    countPaths graph neighbor target
                )
            result

    let part1 (input: string) =
        let graph = parseGraph input
        countPaths graph "you" "out"
