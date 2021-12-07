import sequtils
import strutils

proc getInput(): seq[int] =
    return readAll(stdin).strip.split(',').map(parseInt)

proc getFuelCost(crabs: seq[int], line: int): int =
    var resp = 0
    for c in crabs:
        let diff = abs(c-line)
        let fuel = (diff * (diff + 1)) div 2
        resp += fuel
    return resp

proc process(crabs: seq[int]): int =
    var mini = high(int)
    var maxi = low(int)
    for c in crabs:
        mini = min(mini, c)
        maxi = max(maxi, c)

    var resp = high(int)
    for i in mini..maxi:
        resp = min(resp, getFuelCost(crabs, i))

    return resp

let input = getInput()
let resp = process(input)
echo(resp)

