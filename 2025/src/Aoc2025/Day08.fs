namespace Aoc2025
open System

module Day08 =

    type Pos = int64 * int64 * int64

    type UnionFind<'T> = 
        member this.parent = System.Collections.Generic.Dictionary<'T, 'T>
        member this.size = System.Collections.Generic.Dictionary<'T, int>

        member this.Find(x: 'T) : 'T =
            if not (this.parent.ContainsKey(x)) then
                this.parent.[x] <- x
            if this.parent.[x] <> x then
                this.parent.[x] <- this.Find(this.parent.[x])
            this.parent.[x]

        member this.Union(x: 'T, y: 'T) : unit =
            let rootX = this.Find(x)
            let rootY = this.Find(y)
            if rootX <> rootY then
                if this.size

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

    let dist (x1, y1, z1) (x2, y2, z2) =
        let dx = x1 - x2
        let dy = y1 - y2
        let dz = z1 - z2
        sqrt (float (dx*dx + dy*dy + dz*dz))

    let part1 (input: string) (n: int) =
        let positions = 
            input.Split('\n') |> Array.map (fun line -> 
                let parts = line.Split(',')
                (int64 parts.[0], int64 parts.[1], int64 parts.[2])
        )

        let posPairs = positions |> Array.toList |> pairs

        let closestPairs = 
            List.sortBy (fun (p1, p2) -> dist p1 p2) posPairs 

        let firstNPairs = closestPairs |> List.take n
