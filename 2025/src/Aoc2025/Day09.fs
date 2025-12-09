namespace Aoc2025

module Day09 =
    type Pos = int64 * int64
    type Segment = Pos * Pos

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

    let area (pos1: Pos, pos2: Pos) : int64 =
        let (x1, y1), (x2,y2) = pos1, pos2
        abs (x1 - x2 + 1L) * abs (y1 - y2 + 1L)

    let part1 (input: string) =
        let positions = 
            input.Split('\n') 
            |> Array.map (fun line -> 
                let parts = line.Split(',')
                (int64 parts.[0], int64 parts.[1])
            )

        let posPairs = pairs (Array.toList positions)

        let areas = posPairs |> List.map area

        List.max areas

    let isVertical (segment: Segment) : bool = 
        let ((x1, y1), (x2, y2)) = segment
        x1 = x2

    let isHorizontal (segment: Segment) : bool = 
        let ((x1, y1), (x2, y2)) = segment
        y1 = y2

    let toTopLeftAndBotRight (pos1: Pos) (pos2: Pos) : Pos * Pos = 
        let (x1, y1), (x2, y2) = pos1, pos2
        let (topLeft, bottomRight) = 
            (min x1 x2, min y1 y2), (max x1 x2, max y1 y2)

        topLeft, bottomRight

    let toBotLeftAndTopRight (pos1: Pos) (pos2: Pos) : Pos * Pos = 
        let (x1, y1), (x2, y2) = pos1, pos2
        let (botLeft, topRight) = 
            (min x1 x2, max y1 y2), (max x1 x2, min y1 y2)

        botLeft, topRight 

    let verticalEdges (pos1: Pos) (pos2: Pos) : Segment list =
        let topLeft, bottomRight = toTopLeftAndBotRight pos1 pos2
        let botLeft, topRight = toBotLeftAndTopRight pos1 pos2

        let leftVertical = topLeft, botLeft
        let rightVertical = topRight, bottomRight

        [leftVertical; rightVertical]

    let horizontalEdges (pos1: Pos) (pos2: Pos) : Segment list = 
        let topLeft, bottomRight = toTopLeftAndBotRight pos1 pos2
        let botLeft, topRight = toBotLeftAndTopRight pos1 pos2

        let topHorizontal = topLeft, topRight
        let botHorizontal = botLeft, bottomRight

        [topHorizontal; botHorizontal]

    let isVerticalInside
        (sortedVerticalSegments: Segment array) 
        (verticalSegment: Segment) : bool =
            true

    let isHorizontalInside
        (sortedHorizontalSegments: Segment array) 
        (horizontalSegment: Segment) : bool =
            true


    let isValidSquare 
        (sortedVerticalSegments: Segment array) 
        (sortedHorizontalSegments: Segment array) 
        (pos1: Pos) (pos2: Pos) : bool =

        let vertEdges = verticalEdges pos1 pos2
        let horEdges = horizontalEdges pos1 pos2

        let allVerticalInside =
            vertEdges
            |> List.forall (fun edge -> isVerticalInside sortedVerticalSegments edge) 

        let allHorizontalInside =
            horEdges
            |> List.forall (fun edge -> isHorizontalInside sortedHorizontalSegments edge)

        allVerticalInside && allHorizontalInside

    let part2 (input: string) =
        let positions = 
            input.Split('\n') 
            |> Array.map (fun line -> 
                let parts = line.Split(',')
                Pos (int64 parts.[0], int64 parts.[1])
            )

        let segments : Segment array = 
            Array.pairwise positions

        let verticalSegments = segments |> Array.filter isVertical
        let horizontalSegments = segments |> Array.filter isHorizontal

        let sortedVerticalSegments = 
            verticalSegments |> Array.sortBy (fun ((x1, y1), (x2, y2)) -> x1)
        let sortedHorizontalSegments = 
            horizontalSegments |> Array.sortBy (fun ((x1, y1), (x2, y2)) -> y1)


        let posPairs = 
            pairs (Array.toList positions)

        let validSquares = 
            posPairs 
            |> List.filter (fun (pos1, pos2) -> 
                isValidSquare sortedVerticalSegments sortedHorizontalSegments pos1 pos2
            )

        validSquares 
            |> List.map (fun (p1, p2) -> 
                area (p1, p2 ))
            |> List.max
