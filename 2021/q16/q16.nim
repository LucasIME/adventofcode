import sequtils
import strutils
import sugar
import sets
import tables
import bitops

type ParsePacketResponse = object
    nextIndex: int
    cumulativeValue: int
    shouldStop: bool

proc getInput(): string =
    return readAll(stdin).strip()

proc toBits(n: int): seq[int] =
    var resp: seq[int] = @[bitand(n, 8), bitand(n, 4), bitand(n, 2), bitand(n, 1)].map(x => (if x != 0: 1 else: 0))
    return resp

proc toBits(c: char): seq[int] =
    return toBits(parseHexInt($c))

proc toBits(s: string): seq[int] =
    var resp: seq[int] = @[]
    for c in s:
        resp = resp & toBits(c)
    return resp

proc bitToInt(bits: seq[int]): int =
    var resp = 0
    for i in 0..bits.high:
        resp += bits[i] * (1 shl (bits.high() - i))
    return resp

proc processPacket(input: seq[int], startIndex: int): ParsePacketResponse =
    let version = bitToInt(input[startIndex..startIndex+2])
    let typeId = bitToInt(input[startIndex+3..startIndex+5])
    let payloadStart = startIndex+6

    var i = payloadStart

    if typeId == 4:
        var literal: seq[int] = @[]
        while true:
            let isLast = input[i] == 0
            for j in 1..4:
                inc i
                if i < input.len():
                    literal.add(input[i])
                else:
                    literal.add(0)
            if isLast:
                break
            inc i

        return ParsePacketResponse(nextIndex: i+1, cumulativeValue: version, shouldStop: true)

    let lenghTypeId = input[payloadStart]
    var sum = version

    if lenghTypeId == 0:
        let bitsToRead = 15
        let packetLen = bitToInt(input[payloadStart + 1.. payloadStart + bitsToRead])
        let originalPStart = payloadStart + bitsToRead + 1
        var pStart = originalPStart

        while pStart - originalPStart < packetLen:
            let next = processPacket(input, pStart)
            sum += next.cumulativeValue
            pStart = next.nextIndex
        return ParsePacketResponse(nextIndex: pStart, cumulativeValue: sum, shouldStop:true)

    elif lenghTypeId == 1:
        let bitsToRead = 11
        let packetLen = bitToInt(input[payloadStart + 1.. payloadStart + bitsToRead])
        var pStart = payloadStart + bitsToRead + 1
        for p in 1..packetLen:
            let next = processPacket(input, pStart)
            sum += next.cumulativeValue
            pStart = next.nextIndex
        return ParsePacketResponse(nextIndex: pStart, cumulativeValue: sum, shouldStop:true)

    return ParsePacketResponse(nextIndex: 0, cumulativeValue: 0, shouldStop:true)

proc process(input: string): int =
    var i = 0
    var resp = 0
    let bitInput = toBits(input)
    while true:
        let changes = processPacket(bitInput, i)
        resp += changes.cumulativeValue
        i = changes.nextIndex
        if changes.nextIndex >= bitInput.len() or changes.shouldStop:
            break
    return resp

let input = getInput()
let resp = process(input)
echo(resp)

