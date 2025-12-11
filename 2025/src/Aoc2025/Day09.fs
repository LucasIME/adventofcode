namespace Aoc2025

module Day09 =
    let area ((row1: int64, col1: int64), (row2 : int64, col2 : int64)) : int64 =
        abs (row1 - row2 + 1L) * abs (col1 - col2 + 1L)

    let part1 (input: string) =
        let positions = 
            input.Split('\n') 
            |> Array.map (fun line -> 
                let parts = line.Split(',')
                (int64 parts.[0], int64 parts.[1])
            )

        let posPairs = Utils.pairs (Array.toList positions)

        let areas = posPairs |> List.map area

        List.max areas
