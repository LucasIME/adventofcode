import sequtils
import strutils
import tables

proc toCoord(s: string): (int, int) =
    let vals = s.split(',').map(parseInt)
    return (vals[0], vals[1])

proc toLines(s: string): ((int,int),(int,int)) =
    let sp = s.split(" -> ").map(toCoord)
    return (sp[0], sp[1])

proc getInput(): seq[((int,int),(int,int))] =
    return readAll(stdin).strip.splitLines.map(toLines)

proc addLinePoints(line: ((int,int), (int,int)), visited: var CountTable[(int,int)]): void =
        let (start, final) = line

        let xSlope = (if final[0] > start[0]: 1 elif final[0] < start[0] : -1 else: 0)
        let ySlope = (if final[1] > start[1]: 1 elif final[1] < start[1] : -1 else: 0)

        let xDif = abs(final[0] - start[0])
        let yDif = abs(final[1] - start[1])
        for i in 0..max(xDif, yDif): visited.inc((start[0] + xSlope*i, start[1] + ySlope*i))

proc process(lines: seq[((int,int), (int,int))]): int =
    var visited = initCountTable[(int,int)]()

    for line in lines:
        addLinePoints(line, visited)

    var res = 0

    for k, v in visited.pairs:
        if v > 1:
            inc res

    return res

let input = getInput()
let resp = process(input)
echo(resp)
