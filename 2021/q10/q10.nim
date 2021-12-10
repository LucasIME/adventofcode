import sequtils
import strutils

proc getInput(): seq[string] =
    return readAll(stdin).strip.splitLines

proc getScore(s: string): int =
    var stack: seq[char] = @[]

    var resp = 0
    for c in s:
        case c:
            of '(':
                stack.add(c)
            of '[':
                stack.add(c)
            of '{':
                stack.add(c)
            of '<':
                stack.add(c)
            of ')':
                if len(stack) == 0 or stack[^1] != '(':
                    resp += 3
                else:
                    discard stack.pop()
            of ']':
                if len(stack) == 0 or stack[^1] != '[':
                    resp += 57
                else:
                    discard stack.pop()
            of '}':
                if len(stack) == 0 or stack[^1] != '{':
                    resp += 1197
                else:
                    discard stack.pop()
            of '>':
                if len(stack) == 0 or stack[^1] != '<':
                    resp += 25137
                else:
                    discard stack.pop()
            else:
                return -1000
        if resp > 0:
            return resp
    return resp

proc process(input: seq[string]): int =
    var resp = 0
    for s in input:
        let score = getScore(s)
        resp += score
    return resp

let input = getInput()
let resp = process(input)
echo(resp)

