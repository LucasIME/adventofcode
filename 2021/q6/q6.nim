import sequtils
import strutils

proc getInput(): seq[int] =
    return readAll(stdin).strip.split(',').map(parseInt)

proc updateState(fishes: var seq[int]): void =
    var toBeAdded: seq[int] = @[]
    for i, x in fishes.pairs:
        if x == 0:
            fishes[i] = 6
            toBeAdded.add(8)
        else:
            fishes[i] -= 1
    fishes = fishes & toBeAdded

proc process(fishes: seq[int]): int =
    var state = fishes
    for i in 1..80:
        updateState(state)
    return len(state)

let input = getInput()
let resp = process(input)
echo(resp)
