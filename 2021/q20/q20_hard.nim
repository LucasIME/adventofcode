import sequtils
import strutils
import sugar
import sets
import tables
import bitops

type Input = object
    algo: seq[char]
    image: Table[(int, int), char]
    default: char

type Boundaries = object
    minX: int
    maxX: int
    minY: int
    maxY: int

const LIGHT = '#'
const DARK = '.'

proc getInput(): Input =
    let rawInp = readAll(stdin).strip.split("\n\n")
    let algo = toSeq(rawInp[0].items)

    var image = initTable[(int, int), char]()

    let rawImage = rawInp[1].splitLines().map(s => toSeq(s.items))
    for row in 0..rawImage.high():
        for col in 0..rawImage[row].high():
            let c = rawImage[row][col]
            if c == LIGHT:
                image[(row, col)] = LIGHT

    return Input(algo: algo, image:image, default: DARK)

proc getBoundaries(input: Input): Boundaries =
    var minX = high(int)
    var maxX = low(int)
    var minY = high(int)
    var maxY = low(int)

    for coord in input.image.keys:
        minX = min(minX, coord[1])
        maxX = max(maxX, coord[1])
        minY = min(minY, coord[0])
        maxY = max(maxX, coord[0])

    return Boundaries(minX: minX, maxX: maxX, minY: minY, maxY: maxY)

proc getMaybeVal(row, col: int, input: Input): int =
    var resp = LIGHT
    if (row, col) in input.image:
        resp = input.image[(row, col)]
    else:
        resp = input.default

    return (if resp == LIGHT: 1 else: 0)

proc bitToInt(bits: seq[int]): int =
    var resp = 0
    for i in 0..bits.high:
        resp += bits[i] * (1 shl (bits.high() - i))
    return resp

proc getPointVal(row, col: int, input: Input): char =
    var binArray: seq[int] = @[]

    for rDif in -1..1:
        for cDif in -1..1:
            binArray.add(getMaybeVal(row + rDif, col + cDif, input))

    let index = bitToInt(binArray)

    return input.algo[index]

proc getNextDefault(input: Input): char =
    if input.default == DARK:
        return input.algo[0]
    else:
        return input.algo[bitToInt(newSeqWith(9, 1))]

proc transform(input: Input): Input =
    var nextImage = initTable[(int, int), char]()

    let boundaries = getBoundaries(input)
    #for row in boundaries.minY-1..boundaries.maxY+1:
    #    for col in boundaries.minX-1..boundaries.maxX+1:
    for row in boundaries.minY-5..boundaries.maxY+5:
        for col in boundaries.minX-5..boundaries.maxX+5:
            let pointVal = getPointVal(row, col, input)
            nextImage[(row, col)] = pointVal

    let nextDefault = getNextDefault(input)

    return Input(algo: input.algo, image: nextImage, default: nextDefault)

proc countLight(input: Input): int =
    var resp = 0
    for k in input.image.keys:
        if input.image[k] == LIGHT:
            inc resp
    return resp

proc process(input: Input): int =
    let reps = 50
    var curState = input
    for i in 1..reps:
        curState = transform(curState)

    return countLight(curState)

let input = getInput()
let resp = process(input)
echo(resp)

