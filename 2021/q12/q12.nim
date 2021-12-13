import sequtils
import strutils
import sugar
import sets
import tables

const START = "start"
const END = "end"

proc getInput(): Table[string, seq[string]] =
    var resp = initTable[string, seq[string]]()

    let linex = readAll(stdin).strip.splitLines

    for line in linex:
        let split = line.split('-')
        resp[split[0]] = resp.mgetOrPut(split[0], @[]) & @[split[1]]
        resp[split[1]] = resp.mgetOrPut(split[1], @[]) & @[split[0]]

    return resp

proc isSmall(s:string): bool =
    return isLowerAscii(s[0])

proc expand(input: Table[string, seq[string]], node: string, count: var int, smallVisited: var HashSet[string]) =
    if node == END:
        inc count

    if node notin input:
        return

    for next in input[node]:
        let nextIsSmall = isSmall(next)
        if nextIsSmall:
            let smallWasAlreadyVisited = smallVisited.contains(next)
            if not smallWasAlreadyVisited:
                smallVisited.incl(next)
                expand(input, next, count, smallVisited)
                smallVisited.excl(next)
        else:
            expand(input, next, count, smallVisited)

proc findPaths(input: Table[string, seq[string]]): int =
    var resp = 0
    var smallSet = initHashSet[string]()
    smallSet.incl(START)
    expand(input, START, resp, smallSet)
    return resp


proc process(input: Table[string, seq[string]]): int =
    return findPaths(input)

let input = getInput()
let resp = process(input)
echo(resp)

