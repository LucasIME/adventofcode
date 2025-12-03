namespace Aoc2025

module Day03 =

    let rec joltageHelper(digits: list<int>, maxDigitSoFar: int, maxTotalSoFar: int) : int  =
        match digits with
            | [] -> maxTotalSoFar
            | head :: tail -> 
                let newMaxDigit = max head maxDigitSoFar
                let maxWithCurDigit = 10*maxDigitSoFar + head 
                let newMaxTotal = max maxTotalSoFar maxWithCurDigit
                joltageHelper(tail, newMaxDigit, newMaxTotal)

    let joltage(bank: string) : int =
        let digits =  bank |> Seq.map (fun c -> int c - int '0') |> Seq.toList
        joltageHelper(digits, 0, 0)

    let part1 (input: string) =
        let lines = input.Split('\n')
        
        let joltages = lines |> Array.map joltage

        joltages |> Array.sum
