import sequtils
import strutils
import sugar


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

proc process(grid: seq[seq[int]]): int =
    var resp = 0
    let lowPoints = findLowPoints(grid)
    for point in lowPoints:
        let row  = point[0]
        let col = point[1]
        resp += 1 + grid[row][col]
    return resp

let input = getInput()
let resp = process(input)
echo(resp)

