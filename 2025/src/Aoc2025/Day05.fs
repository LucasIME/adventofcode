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

    let canMerge (range1: Range, range2: Range) : bool =
        let (min1, max1) = range1
        let (min2, max2) = range2
        (min1 >= min2 && min1 <= max2) || (min2 >= min1 && min2 <= max1)

    let mergeRange (range1: Range, range2: Range) : Range =
        let (min1, max1) = range1
        let (min2, max2) = range2
        (min min1 min2, max max1 max2)

    let rec mergeIntervals (sortedIntervals: Range list) : Range list =
        match sortedIntervals with
        | [] -> []
        | [ head] -> [ head]
        | head :: next :: tail ->
            if canMerge(head, next) then
                let merged = mergeRange(head, next)
                mergeIntervals(merged :: tail)
            else
                head :: mergeIntervals(next :: tail)

    let allValid (ranges: Range array) : int64 =
        let sortedRanges = Array.sort ranges |> Array.toList
        let mergedIntervals = mergeIntervals sortedRanges

        mergedIntervals |> List.sumBy (fun (min, max) -> int64 (max - min + 1L))

    let part2 (input: string) =
        let (ranges, ingredients) = parse input
        allValid ranges