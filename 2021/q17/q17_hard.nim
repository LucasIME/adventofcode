import sequtils
import strutils
import sugar
import sets
import tables

type Area = object
    xRange: (int, int)
    yRange: (int, int)

proc parseRange(s: string): (int, int) =
    let splits = s.split("..")
    return (parseInt(splits[0]), parseInt(splits[1]))

proc getInput(): Area =
    let ranges = readAll(stdin).strip()[15..^1].split(", y=").map(parseRange)
    return Area(xRange: ranges[0], yRange: ranges[1])

proc inArea(area: Area, xPos, yPos: int): bool =
    return xPos >= area.xRange[0] and xPos <= area.xRange[1] and yPos >= area.yRange[0] and yPos <= area.yRange[1]

proc vLandsInArea(area: Area, xVel, yVel: int): bool =
    var xPos = 0
    var yPos = 0
    var xV = xVel
    var yV = yVel
    for i in 0..1000:
        xPos += xV
        yPos += yV
        xV += (if xV > 0: -1 elif xV < 0: 1 else: 0)
        yV -= 1
        if inArea(area, xPos, yPos):
            return true

    return false

proc process(area: Area): int =
    var lands = 0

    for xV in -1000..1000:
        for yV in -1000..1000:
            if vLandsInArea(area, xV, yV):
                inc lands

    return lands

let input = getInput()
let resp = process(input)
echo(resp)

