import sequtils
import strutils
import sugar
import sets

proc getInput(): seq[seq[int]] =
    return readAll(stdin).strip.splitLines.map(s => s.toSeq().map(c => parseInt($c)))

proc isValid(row, col:int): bool =
    return row >= 0 and row <= 9 and col >= 0 and col <= 9

proc getNeighs(row, col:int): seq[(int, int)] =
    var resp: seq[(int, int)] = @[]
    for rDif in -1..1:
        for cDif in -1..1:
            if (rDif != 0 or cDif != 0) and isValid(row + rDif, col + cDif):
                resp.add((row + rDif, col + cDif))
    return resp

proc flash(input: var seq[seq[int]], row, col:int, flashed: var HashSet[(int, int)]): void=
    if (row, col) in flashed:
        return

    let curVal = input[row][col]

    if curVal == 9:
        input[row][col] = 0
        flashed.incl((row, col))
        for neigh in getNeighs(row, col):
            flash(input, neigh[0], neigh[1], flashed)
    else:
        inc input[row][col]

    return

proc step(input: var seq[seq[int]]): int =
    var flashed = initHashSet[(int, int)]()
    for row in 0..9:
        for col in 0..9:
            flash(input, row, col, flashed)

    return len(flashed)

proc process(input: seq[seq[int]]): int =
    var resp = 0
    var grid = input
    for step in 1..100:
        resp += step(grid)

    return resp

let input = getInput()
let resp = process(input)
echo(resp)

