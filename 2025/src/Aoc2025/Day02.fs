namespace Aoc2025

module Day02 =
    let parseEntry (s :string) = 
        let parts = s.Trim().Split('-')
        let start = int64 parts.[0]
        let endp = int64 parts.[1]
        (start, endp)

    let isInvalid(value: int64) =
        let str = value.ToString()
        let length = str.Length
        if length % 2 = 1 then false else
            let mid = length / 2
            let left = str.[..mid - 1]
            let right = str.[mid..]
            left = right

    let part1 (input: string) =
        let lines = input.Split(',')
        let intervals = lines |> Array.map parseEntry
        let expandedIntervals = intervals |> Array.collect (fun (s, e) -> [|s .. e|])
        expandedIntervals |> Array.filter isInvalid |> Array.sum
