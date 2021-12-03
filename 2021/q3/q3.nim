import sequtils
import strutils
import tables

proc getInput(): seq[string] =
  return readAll(stdin).strip.splitLines

proc buildFreqMap(input: seq[string]): Table[int, Table[char, int]] =
  var freq = initTable[int, initTable[char, int]()]()

  for word in input:
    for i in 0..<len(word):
      let c = word[i]
      inc freq.mgetOrPut(i, initTable[char, int]()).mgetOrPut(c, 0)

  return freq

proc getGamma(freqMap: Table[int, Table[char, int]]): string =
  var gamma = ""
  for i in 0..<len(freqMap):
    if freqMap[i]['0'] > freqMap[i]['1']:
      gamma = gamma & "0"
    else:
      gamma = gamma & "1"
  return gamma

proc invertBinString(input: string): string =
  var resp = ""
  for c in input:
    if c == '0':
      resp.add("1")
    else:
      resp.add("0")
  return resp

proc getValFromBin(input: string): int =
  var resp = 0
  for i in 0..<input.len:
    let realIndex = input.len - 1 - i
    resp += parseInt($input[realIndex]) shl i

  return resp

proc process(input: seq[string]): int =
  var freq = buildFreqMap(input)

  let gamma = getGamma(freq)
  let epsilon = invertBinString(gamma)

  let gammaValue = getValFromBin(gamma)
  let epsilonValue = getValFromBin(epsilon)

  return gammaValue * epsilonValue

let input = getInput()
let resp = process(input)
echo(resp)
