namespace Aoc2025

module Day05 =

    type Range = int64 * int64

    let parse (input: string) : Range array * int64 array = 
        match input.Split("\n\n") with 
            | [| rawRanges; rawIngredients|] ->
                let ranges : Range array = 
                    rawRanges.Split('\n')
                    |> Array.map (fun line -> 
                        let parts = line.Split('-') 
                        (int64 parts.[0], int64 parts.[1]))
                let ingredients = 
                    rawIngredients.Split('\n')
                    |> Array.map int64
                ranges, ingredients
            | _ -> failwith "Invalid input format"

    let isValid (ranges: Range array) (ingredient: int64) : bool =
        ranges
        |> Array.exists (fun (min, max) -> ingredient >= min && ingredient <= max)

    let solve (ranges: Range array) (ingredients: int64 array): int =
        let validIngredients = 
            ingredients
                |> Array.filter (fun ingredient -> isValid ranges ingredient)
        validIngredients.Length

    let part1 (input: string) =
        let (ranges, ingredients) = parse input
        solve ranges ingredients