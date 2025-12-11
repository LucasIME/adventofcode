namespace Aoc2025

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

    let rec countPathsMemo (graph: Map<string, string list>) =
        Utils.memoizeRec (fun self (start: string, target: string, hasSeenFft: bool, hasSeenDac: bool)  ->
            if start = target then
                if (hasSeenFft && hasSeenDac) then 1L else 0L
            else
                let neighs = graph |> Map.tryFind start |> Option.defaultValue []
                let newHasSeenFft = hasSeenFft || (start = "fft")
                let newHasSeenDac = hasSeenDac || (start = "dac")
                let result = 
                    neighs 
                    |> List.sumBy (fun neighbor -> 
                        self (neighbor, target, newHasSeenFft, newHasSeenDac)
                    )
                result
            )
    
    let part1 (input: string) =
        let graph = parseGraph input
        countPathsMemo graph ("you", "out", true, true)

    let part2 (input: string) =
        let graph = parseGraph input
        countPathsMemo graph ("svr", "out", false, false)
