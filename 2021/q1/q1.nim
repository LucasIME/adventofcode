import sequtils
import strutils

proc getInput(): seq[int] =
  return readAll(stdin).strip.splitLines.map(parseInt)

proc process(input: seq[int]): int =
  var count = 0

  for i in 1..input.high:
    if input[i] > input[i-1]:
      inc count

  return count

let input = getInput()
let resp = process(input)
echo(resp)
