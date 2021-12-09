import sequtils
import strutils
import tables
import sets

type Entry = object
    inp: seq[string]
    outp: seq[string]

proc isEasilyDecidable(s: string): bool =
    return len(s) == 2 or len(s) == 4 or len(s) == 3 or len(s) == 7

proc getEasyDefinition(s: string): int =
    case len(s):
        of 2:
            return 1
        of 4:
            return 4
        of 3:
            return 7
        of 7:
            return 8
        else:
            return -1

proc toSet(s: string): HashSet[char] =
    return toHashSet(s.toSeq)

proc decode(e: Entry): Table[HashSet[char], int] =
    var seqToNum = initTable[string, int]()
    var numToSeq = initTable[int, string]()
    for i in e.inp:
        if isEasilyDecidable(i):
            seqToNum[i] = getEasyDefinition(i)
            numToSeq[seqToNum[i]] = i

    # find 6
    var oneChars = toSet(numToSeq[1])
    for i in e.inp:
        if len(i) == 6:
            for c in oneChars:
                if c notin i:
                    seqToNum[i] = 6
                    numToSeq[6] = i

    # find 9
    for i in e.inp:
        if len(i) == 6 and i != numToSeq[6]:
            var allFourFit = true
            for c in numToSeq[4]:
                if c notin i:
                    seqToNum[i] = 0
                    numToSeq[0] = i
                    allFourFit = false
            if allFourFit:
                seqToNum[i] = 9
                numToSeq[9] = i
    # find 3
    for i in e.inp:
        if len(i) == 5:
            var allOneFit = true
            for c in numToSeq[1]:
                if c notin i:
                    allOneFit = false
            if allOneFit:
                numToSeq[3] = i
                seqToNum[i] = 3

    # find 5 and 2
    for i in e.inp:
        if len(i) == 5 and i != numToSeq[3]:
            let sixSet = toSet(numToSeq[6])
            let iSet = toSet(i)
            var allIn = true
            for c in iSet:
                if c notin sixSet:
                    allIn = false
            if allIn:
                numToSeq[5] = i
                seqToNum[i] = 5
            else:
                numToSeq[2] = i
                seqToNum[i] = 2

    var resp = initTable[HashSet[char], int]()
    for s, v in seqToNum.pairs:
      resp[toSet(s)] = v
    return resp

proc toEntry(s: string): Entry =
    let a = s.split(" | ")
    return Entry(inp: a[0].split(" "), outp: a[1].split(" "))

proc getInput(): seq[Entry] =
    return readAll(stdin).strip.splitLines.map(toEntry)

proc process(entries: seq[Entry]): int =
    var resp = 0
    for e in entries:
        let table = decode(e)
        var curNum = 0
        var mul = 1000
        for o in e.outp:
            curNum += mul * table[toSet(o)]
            mul = mul div 10
        resp += curNum
    return resp

let input = getInput()
let resp = process(input)
echo(resp)

