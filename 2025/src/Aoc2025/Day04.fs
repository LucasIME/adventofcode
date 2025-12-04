namespace Aoc2025

module Day04 =

    let isValid (grid: char array array) (row: int, col: int): bool =
        row >= 0 && row < grid.Length && col >= 0 && col < grid[row].Length

    let isAccessible (grid : char array array) (row: int, col: int, ch: char): bool = 
        let diagonalsVec = [ (-1, -1); (-1, 1); (1, 1); (1, -1)]
        let normalVec = [ (1, 0); (-1, 0); (0, 1); (0, -1) ]
        let neighVec =  normalVec @ diagonalsVec

        let potentialNeighs = neighVec |> List.map (fun (dr, dc) -> (row + dr, col + dc))
        let validNeighs = potentialNeighs |> List.filter (fun neighPos -> isValid grid neighPos)

        let neighSymbols = validNeighs |> List.map (fun (row, col) -> grid[row][col])

        let paperNeighs = neighSymbols |> List.filter (fun c -> c = '@')

        paperNeighs.Length < 4 && ch = '@'

    let countAccessible(grid) =
        let coords = 
            grid 
            |> Array.mapi (fun rowIndex row -> 
                    row |> Array.mapi (fun colIndex ch -> (rowIndex, colIndex, ch))
                )
                |> Array.collect id

        let accessible = 
            coords 
                |> Array.filter (fun pos -> isAccessible grid pos )

        accessible |> Array.length


    let part1 (input: string) =
        let lines = input.Split('\n')
        let grid = lines |> Array.map Seq.toArray

        countAccessible grid

    let rec clean grid : int =
        let acessible = countAccessible grid

        let nextGrid = 
            grid
                |>  Array.mapi (fun rowIndex row -> 
                    row |> Array.mapi (fun colIndex ch -> 
                        if isAccessible grid (rowIndex, colIndex, ch) then '.' else ch))

        if acessible = 0 then 0 else acessible + clean nextGrid

    let part2 (input: string) = 
        let lines = input.Split('\n')
        let grid = lines |> Array.map Seq.toArray
        clean grid