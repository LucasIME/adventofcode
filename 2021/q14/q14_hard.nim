import sequtils
import strutils
import sugar
import sets
import tables

type
    Input = ref object
        polymer: string
        rules: Table[string, string]


proc getInput(): Input =

    let rawInp = readAll(stdin).strip.split("\n\n")

    let polymer = rawInp[0]

    var rules = initTable[string, string]()
    for rawRule in  rawInp[1].splitLines.map(s => s.split(" -> ")).map(v => (v[0], v[1])):
        rules[rawRule[0]] = rawRule[1]

    return Input(polymer: polymer, rules: rules)

proc react(polymer: Table[string, int], rules: Table[string, string]): Table[string, int] =
    var resp = initTable[string, int]()
    for k, v in polymer.pairs():
        resp[k] = v

    for p, count in polymer.pairs():
        if p in rules:
            let newC = rules[p]
            let newCombs = @[p[0] & newC, newC & p[1]]
            for comb in newCombs:
                resp[comb] = resp.mgetOrPut(comb, 0) + count
            resp[p] -= count
            if resp[p] == 0:
                resp.del(p)
    return resp

proc toCharFreqMap(t: Table[string, int]): Table[char, int] =
    var resp = initTable[char, int]()
    for s, v in t.pairs():
        for c in s:
            resp[c] = resp.mgetOrPut(c, 0) + v
    return resp

proc toPairFreqMap(s: string): Table[string, int] =
    var resp = initTable[string, int]()
    for i in 0..<s.high:
        let pair = s[i..i+1]
        resp[pair] = resp.mgetOrPut(pair, 0) + 1
    return resp

proc getMax(t: Table[char, int]): int =
    var maxi = 0
    for k, v in t.pairs():
        maxi = max(maxi, v)
    return maxi

proc getMini(t: Table[char, int]): int =
    var mini = high(int)
    for k, v in t.pairs():
        mini = min(mini, v)
    return mini

proc normalize(t: var Table[char, int], first, last: char): void =
    for k, v in t.pairs():
        t[k] = v div 2

    inc t[first]
    inc t[last]

proc process(input: Input): int =
    var polymer = input.polymer
    let first = polymer[0]
    let last = polymer[^1]
    var freqMap = toPairFreqMap(polymer)
    for i in 0..<40:
        freqMap = react(freqMap, input.rules)

    var cFreqMap = toCharFreqMap(freqMap)
    normalize(cfreqMap, first, last)

    let maxi = getMax(cfreqMap)
    let mini = getMini(cfreqMap)
    return maxi - mini

let input = getInput()
let resp = process(input)
echo(resp)

