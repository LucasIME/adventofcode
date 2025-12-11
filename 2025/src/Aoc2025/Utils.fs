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