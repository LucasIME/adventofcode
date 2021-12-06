import sequtils
import strutils
import tables

proc getInput(): seq[int] =
    return readAll(stdin).strip.split(',').map(parseInt)

proc getProducedFishes(fish: int, days: int, memo: var Table[(int, int), int]): int =
    if memo.hasKey((fish, days)):
        return memo[(fish, days)]

    if fish > days:
        return 0

    var resp = 0
    var curDays = days
    var curFish = fish
    while curDays > 0:
        if curFish == 0:
            curFish = 6
            resp += 1 + getProducedFishes(8, curDays-1, memo)
        else:
            curFish -= 1
        curDays -= 1

    memo[(fish, days)] = resp

    return resp

proc process(fishes: seq[int]): int =
    var memo: Table[(int, int), int] = initTable[(int, int), int]()
    var resp = len(fishes)

    for fish in fishes:
        resp += getProducedFishes(fish, 256, memo)
    return resp

let input = getInput()
let resp = process(input)
echo(resp)
