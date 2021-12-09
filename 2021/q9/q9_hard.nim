import sequtils
import strutils
import sugar
import sets
import algorithm


proc getInput(): seq[seq[int]] =
    return readAll(stdin).strip.splitLines.map(s => s.toSeq().map(c => parseInt($c)))

proc isInsideGrid(grid: seq[seq[int]], p:(int, int)): bool =
    return p[0] >= 0 and p[0] < len(grid) and p[1] >= 0 and p[1] < len(grid[p[0]])

proc getValidNeighs(grid: seq[seq[int]], row, col: int): seq[(int, int)] =
    var resp: seq[(int, int)] = @[]
    for dif in @[(-1, 0), (1, 0), (0, 1), (0, -1)]:
        if isInsideGrid(grid, (row + dif[0], col + dif[1])):
            resp.add((row + dif[0], col + dif[1]))
    return resp

proc findLowPoints(grid: seq[seq[int]]): seq[(int, int)] =
    var resp: seq[(int, int)] = @[]
    for row in 0..grid.high:
        for col in 0..grid[row].high:
            let neighs = getValidNeighs(grid, row, col)
            var isLow = true
            let curVal = grid[row][col]
            for coord in neighs:
                if grid[coord[0]][coord[1]] <= curVal:
                    isLow = false
                    break
            if isLow:
                resp.add((row, col))

    return resp

proc getBasinSize(p: (int, int), grid: seq[seq[int]], visited: var HashSet[(int, int)]): int =
    if p in visited:
        return 0

    let curVal = grid[p[0]][p[1]]

    if curVal == 9:
        return 0

    visited.incl(p)

    var resp = 1
    for neigh in getValidNeighs(grid, p[0], p[1]):
        let neighVal = grid[neigh[0]][neigh[1]]
        if neighVal > curVal:
            resp += getBasinSize(neigh, grid, visited)

    return resp

proc process(grid: seq[seq[int]]): int =
    let lowPoints = findLowPoints(grid)
    var basins: seq[int] = @[]
    var visited = initHashSet[(int, int)]()

    for point in lowPoints:
        let basinSize = getBasinSize(point, grid, visited)
        basins.add(basinSize)

    basins.sort()

    return foldl(basins[^3..^1], a * b, 1)

let input = getInput()
let resp = process(input)
echo(resp)

