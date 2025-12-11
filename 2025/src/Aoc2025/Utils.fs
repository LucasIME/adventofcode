namespace Aoc2025

open System.Collections.Generic

module Utils =
    let memoizeRec f = 
        let cache = Dictionary<_, _>()
        let rec g x =
            match cache.TryGetValue x with
            | (true, v) -> v
            | _ ->
                let v = f g x
                cache.[x] <- v
                v
        g

    let pairs list =
        list
        |> List.mapi (fun i x -> 
            list
            |> List.mapi (fun j y -> 
                (i, j, x, y)
            )
            |> List.filter (fun (i, j, _, _) -> j > i)
            |> List.map (fun (_, _, x, y) -> (x, y))
        )
        |> List.concat