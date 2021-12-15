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

proc expand(input: Table[string, seq[string]], node: string, count: var int, smallVisited: var Table[string, int], anyTwo: bool) =
    if node == END:
        inc count
        return

    if node notin input:
        return

    for next in input[node]:
        let nextIsSmall = isSmall(next)
        if nextIsSmall:
            let smallWasAlreadyVisited = smallVisited.contains(next)
            if not smallWasAlreadyVisited:
                smallVisited[next] = 1
                expand(input, next, count, smallVisited, anyTwo)
                smallVisited.del(next)
            elif smallWasAlreadyVisited and not anyTwo and next != START:
                smallVisited[next] = smallVisited[next] + 1
                expand(input, next, count, smallVisited, true)
                smallVisited[next] = 1

        else:
            expand(input, next, count, smallVisited, anyTwo)

proc findPaths(input: Table[string, seq[string]]): int =
    var resp = 0
    var smallSet = initTable[string, int]()
    smallSet[START] = 1
    expand(input, START, resp, smallSet, false)
    return resp


proc process(input: Table[string, seq[string]]): int =
    return findPaths(input)

let input = getInput()
let resp = process(input)
echo(resp)

