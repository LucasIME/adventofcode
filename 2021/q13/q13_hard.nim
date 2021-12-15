import sequtils
import strutils
import sugar
import sets
import tables

type
    Input = ref object
        entries: seq[(int, int)]
        folds: seq[(string, int)]


proc getInput(): Input =

    let rawInp = readAll(stdin).strip.split("\n\n")

    let entries = rawInp[0].splitLines.map(s => s.split(',').map(parseInt)).map(v => (v[0], v[1]))
    let folds = rawInp[1].splitLines.map(s => s[11..^1].split('=')).map(v => (v[0], parseInt(v[1])))

    return Input(entries: entries, folds: folds)

proc foldPoint(point: (int, int), dir: string, line: int): (int, int) =
    if dir == "x":
        if point[0] < line:
            return point
        else:
            let newX = line - (point[0] - line)
            return (newX, point[1])
    elif dir == "y":
        if point[1] < line:
            return point
        else:
            let newY = line - (point[1] - line)
            return (point[0], newY)

    return point

proc fold(points: HashSet[(int, int)], dir: string, line: int): HashSet[(int, int)] =
    return points.map(p => foldPoint(p, dir, line))

proc toMatrix(points: HashSet[(int, int)]): seq[seq[char]] =
    var maxX = 0
    var maxY = 0
    for p in points:
        maxX = max(p[0], maxX)
        maxY = max(p[1], maxY)
    var matrix = newSeqWith(maxY+1, newSeqWith(maxX+1, '.'))

    for p in points:
        matrix[p[1]][p[0]] = '#'
    return matrix

proc printMatrix(m: seq[seq[char]]) =
    for line in m:
        echo line.join()

proc process(input: Input): seq[seq[char]]=
    var pointSet = input.entries.toHashSet()
    var visiblePoints = pointSet
    for f in input.folds:
        visiblePoints = fold(visiblePoints, f[0], f[1])
    return toMatrix(visiblePoints)

let input = getInput()
let resp = process(input)
printMatrix(resp)

