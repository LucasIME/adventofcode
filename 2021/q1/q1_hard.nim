import sequtils
import strutils

proc getInput(): seq[int] =
  return readAll(stdin).strip.splitLines.map(parseInt)

proc process(input: seq[int]): int =
  var count = 0

  var tripletSum = input[0] + input[1] + input[2]

  for i in 3..input.high:
    let newSum = tripletSum + input[i] - input[i-3]
    if newSum > tripletSum:
      inc count

  return count

let input = getInput()
let resp = process(input)
echo(resp)
