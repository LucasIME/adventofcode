import sequtils
import strutils

type Entry = object
    inp: seq[string]
    outp: seq[string]

proc isEasilyDecidable(s: string): bool =
    return len(s) == 2 or len(s) == 4 or len(s) == 3 or len(s) == 7

proc toEntry(s: string): Entry =
    let a = s.split(" | ")
    return Entry(inp: a[0].split(" "), outp: a[1].split(" "))

proc getInput(): seq[Entry] =
    return readAll(stdin).strip.splitLines.map(toEntry)

proc process(entries: seq[Entry]): int =
    var resp = 0
    for e in entries:
        for o in e.outp:
            if isEasilyDecidable(o):
                inc resp
    return resp

let input = getInput()
let resp = process(input)
echo(resp)

