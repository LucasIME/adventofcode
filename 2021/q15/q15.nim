import sequtils
import strutils
import sugar
import sets
import tables

proc getInput(): seq[seq[int]] =
    return readAll(stdin).strip.splitLines.map(s => s.toSeq().map(c => parseInt($c)))

type Entry = object
    point: (int, int)
    priority: int

proc `<`(a, b: Entry): bool = a.priority < b.priority

proc isValid(p:(int, int), m: seq[seq[int]]): bool =
    return p[0] >= 0 and p[0] < len(m) and p[1] >= 0 and p[1] < len(m[0])

proc getNeighs(p:(int, int), m: seq[seq[int]]): seq[(int, int)] =
    var resp: seq[(int, int)] = @[]
    for dif in @[(-1, 0), (1, 0), (0, -1), (0, 1)]:
        let newP = (p[0] + dif[0], p[1] + dif[1])
        if isValid(newP, m):
            resp.add(newP)

    return resp

proc process(m: seq[seq[int]]): int =
    var notVisited = initHashSet[(int, int)]()
    var distM = newSeqWith(m.len(), newSeqWith(m[0].len(), high(int)))
    distM[0][0] = 0

    for i in 0..m.high:
        for j in 0..m[i].high:
            notVisited.incl((i, j))

    var prioQ = initHeapQueue[Entry]()
    prioQ.push(Entry( point: (0,0), priority: 0))
    while len(notVisited) > 0:
        let entry = prioQ.pop()
        let cur = entry.point

        if cur notin notVisited:
            continue

        for n in getNeighs(cur, m):
            distM[n[0]][n[1]] = min(distM[n[0]][n[1]],  distM[cur[0]][cur[1]] + m[n[0]][n[1]])
            prioQ.push(Entry(point: n, priority: distM[n[0]][n[1]]))
        notVisited.excl(cur)
    return distM[^1][^1] - distM[0][0]

let input = getInput()
let resp = process(input)
echo(resp)

