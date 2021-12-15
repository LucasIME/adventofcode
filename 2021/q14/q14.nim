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

proc react(polymer: string, rules: Table[string, string]): string =
    var resp = ""
    for i in 0..<polymer.high:
        let pair = polymer[i..i+1]
        if rules.contains(pair):
            resp = resp & $(pair[0]) & rules[pair]
        else:
            resp &= $(pair[0])
    return resp & $(polymer[^1])

proc toFreqMap(s: string): Table[char, int] =
    var resp = initTable[char, int]()
    for c in s:
        resp[c] = resp.mgetOrPut(c, 0) + 1
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

proc process(input: Input): int =
    echo(input.polymer, input.rules)
    var polymer = input.polymer
    for i in 0..<10:
        polymer = react(polymer, input.rules)

    let freqMap = toFreqMap(polymer)
    let maxi = getMax(freqMap)
    let mini = getMini(freqMap)
    return maxi - mini

let input = getInput()
let resp = process(input)
echo(resp)

