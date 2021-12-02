import sequtils
import strutils
import sugar

proc getInput(): seq[(string, int)] =
  return readAll(stdin).strip.splitLines.map((s) => s.split(" ")).map((t) => (t[0], parseInt(t[1])))

proc process(input: seq[(string, int)]): int =
  var horizontal = 0
  var depth = 0
  var aim = 0

  for intruction in input:
    let (dir, size) = intruction
    if dir == "forward":
      horizontal += size
      depth += aim * size
    elif dir == "down":
      aim += size
    elif dir == "up":
      aim -= size

  return horizontal * depth

let input = getInput()
let resp = process(input)
echo(resp)
