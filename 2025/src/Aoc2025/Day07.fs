namespace Aoc2025
open System

module Day07 =

    type Pos = int * int

    let parse (input: string) : char array array * Pos =
        let lines = input.Split("\n")

        let grid = 
            lines
            |> Array.mapi (fun r line -> 
                line.ToCharArray()
            )

        let start = 
            grid 
            |> Array.mapi (fun r row -> 
                row 
                |> Array.tryFindIndex (fun c -> c = 'S') 
                |> Option.map (fun c -> (r, c))
            )
            |> Array.tryFind Option.isSome
            |> Option.bind id
            |> Option.defaultWith (fun () -> failwith "Start position not found")

        grid, start

    let rec calcSplit (grid: char array array, beans: Set<Pos>, splits: int) : int =
        let nextBeans = 
            beans 
            |> Set.toArray
            |> Array.collect (fun (r, c) -> 
                if r+1 >= grid.Length then
                    [| |]
                elif grid.[r+1].[c] = '^' then
                    [| (r+1, c - 1); (r+1, c + 1); |]
                else
                    [| (r+1, c) |]
            )

        let newSplits = 
            beans 
            |> Set.toList
            |> List.map (fun (r, c) -> 
                if r+1 >= grid.Length then
                    0
                elif grid.[r+1].[c] = '^' then
                    1
                else
                    0
            )
            |> List.sum

        if Array.isEmpty nextBeans then
            splits + newSplits
        else
            calcSplit(grid, Set.ofArray nextBeans, splits + newSplits)


    let part1 (input: string) =
        let (grid , start) = parse input
        calcSplit(grid, Set.singleton start, 0)