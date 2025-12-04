namespace Aoc2025

module Day03 =
    let rec joltage (digits: int) (bank: array<int64>) : int64 = 
        if digits = 1 then
            bank |> Array.max |> int64
        else
            let prefix = bank |> Array.take (bank.Length - digits + 1)
            let (leftmostBiggestDigit, leftmostBiggestDigitIndex) = 
                prefix |> Array.mapi (fun i v -> v, i) |> Array.maxBy (fun (v, i) -> (v, -i))
            let baseNum = leftmostBiggestDigit * (pown 10L (digits - 1)) 
            baseNum + (joltage (digits - 1) bank[leftmostBiggestDigitIndex+1..])

    let solve (input: string) n =
        let lines = input.Split('\n')
        
        let lineAsDigits = 
            lines 
                |> Array.map (fun line -> 
                    line 
                        |> Seq.map (fun c -> int64 c - int64 '0') |> Seq.toArray)
        let joltages = 
            lineAsDigits 
            |> Array.map (fun line -> joltage n line)

        joltages |> Array.sum


    let part1 (input: string) =
        solve input 2


    let part2 (input: string) =
        solve input 12
