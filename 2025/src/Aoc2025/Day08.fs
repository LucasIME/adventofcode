namespace Aoc2025

module Day08 =

    type Pos = int64 * int64 * int64

    type UnionFind<'T when 'T : equality>() = 
        let parent = System.Collections.Generic.Dictionary<'T, 'T>()
        let size = System.Collections.Generic.Dictionary<'T, int>()

        member this.Find(x: 'T) : 'T =
            if not (parent.ContainsKey(x)) then
                parent.[x] <- x
                size.[x] <- 1

            if parent.[x] <> x then
                this.Find(parent.[x])
            else
                x

        member this.Union(x: 'T, y: 'T) : unit =
            let rootX = this.Find(x)
            let rootY = this.Find(y)

            let xHeight = size.[rootX]
            let yHeight = size.[rootY]

            if yHeight = xHeight then
                parent[rootY] <- rootX
                size[rootX] <- xHeight + 1
            elif yHeight < xHeight then
                parent[rootY] <- rootX
            else
                parent[rootX] <- rootY

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

        let posPairs = positions |> Array.toList |> Utils.pairs

        let closestPairs = 
            List.sortBy (fun (p1, p2) -> dist p1 p2) posPairs 

        let firstNPairs = closestPairs |> List.take n

        let unionFind = UnionFind<Pos>()

        firstNPairs |> Seq.iter (fun (pos1, pos2) -> unionFind.Union(pos1, pos2))

        let distinctPoints = firstNPairs |> List.collect (fun (p1, p2) -> [p1; p2])  |> List.distinct

        let countBySet = distinctPoints |> List.countBy (fun p -> unionFind.Find(p))

        let setByDecreasingCount = List.sortByDescending (fun (set, count ) -> count) countBySet

        let threeLargest = setByDecreasingCount |> List.take 3

        let threeCount = threeLargest |> List.map (fun (pos, count) -> count)

        threeCount |> List.reduce (*)


    let part2 (input: string) =
        let positions = 
            input.Split('\n') |> Array.map (fun line -> 
                let parts = line.Split(',')
                (int64 parts.[0], int64 parts.[1], int64 parts.[2])
        )

        let posPairs = positions |> Array.toList |> Utils.pairs

        let closestPairs = 
            List.sortBy (fun (p1, p2) -> dist p1 p2) posPairs 

        let unionFind = UnionFind<Pos>()


        let mutable lastP1 : Pos = (0, 0 ,0)
        let mutable lastP2 : Pos = (0, 0 ,0)
        Seq.takeWhile (fun (p1, p2) -> 
            unionFind.Union(p1, p2)

            let distinctSets =  positions |> Array.map unionFind.Find |> Array.distinct |> Array.length

            lastP1 <- p1
            lastP2 <- p2

            distinctSets <> 1
        ) closestPairs
        |> Seq.toList
        |> ignore

        let (x1, _, _) = lastP1
        let (x2, _, _) = lastP2

        x1 * x2
