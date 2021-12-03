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

proc buildFreqArray(freqMap: Table[int, Table[char, int]]): seq[int] =
  var resp: seq[int] = newSeq[int](freqMap.len)
  for i in 0..<len(freqMap):
    let zeroCount = freqMap[i].getOrDefault('0')
    let oneCount = freqMap[i].getOrDefault('1')

    if zeroCount > oneCount:
      resp[i] = 0
    else:
      resp[i] = 1
  return resp

proc getValFromBin(input: string): int =
  var resp = 0
  for i in 0..<input.len:
    let realIndex = input.len - 1 - i
    resp += parseInt($input[realIndex]) shl i

  return resp

proc matchesMostCommon(input: string, freqArray: seq[int], index: int): bool =
  return parseInt($input[index]) == freqArray[index]

proc process(input: seq[string]): int =
  var freqMap = buildFreqMap(input)
  var freqArray = buildFreqArray(freqMap)

  var oxySet = input
  var i = 0
  while oxySet.len > 1:
    oxySet = oxySet.filterIt(matchesMostCommon(it, freqArray, i))
    freqMap = buildFreqMap(oxySet)
    freqArray = buildFreqArray(freqMap)
    i += 1

  var co2Set = input
  freqMap = buildFreqMap(co2Set)
  freqArray = buildFreqArray(freqMap)
  i = 0
  while co2Set.len > 1:
    co2Set = co2Set.filterIt(not matchesMostCommon(it, freqArray, i))
    freqMap = buildFreqMap(co2Set)
    freqArray = buildFreqArray(freqMap)
    i += 1

  let oxyVal = getValFromBin(oxySet[0])
  let co2Val = getValFromBin(co2Set[0])

  return oxyVal * co2Val


let input = getInput()
let resp = process(input)
echo(resp)
