import sequtils
import strutils
import tables

proc toCoord(s: string): (int, int) =
    let vals = s.split(',').map(parseInt)
    return (vals[0], vals[1])

proc toLines(s: string): ((int,int),(int,int)) =
    let sp = s.split(" -> ").map(toCoord)
    return (sp[0], sp[1])

proc isHorizontalOrVertical(line: ((int, int), (int,int))): bool =
    return line[0][0] == line[1][0] or line[0][1] == line[1][1]

proc getInput(): seq[((int,int),(int,int))] =
    return readAll(stdin).strip.splitLines.map(toLines).filter(isHorizontalOrVertical)

proc process(lines: seq[((int,int), (int,int))]): int =
    var visited = initCountTable[(int,int)]()

    for i in 0..<lines.len:
        let (start, final) = lines[i]
        for row in min(start[0],final[0])..max(start[0],final[0]):
            for col in min(start[1],final[1])..max(start[1],final[1]):
                visited.inc((row,col))

    var res = 0

    for k, v in visited.pairs:
        if v > 1:
            inc res

    return res

let input = getInput()
let resp = process(input)
echo(resp)
