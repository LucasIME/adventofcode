import sequtils
import strutils
import algorithm

proc getInput(): seq[string] =
    return readAll(stdin).strip.splitLines

proc getScore(s: string): int =
    var stack: seq[char] = @[]

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
                    return 0
                else:
                    discard stack.pop()
            of ']':
                if len(stack) == 0 or stack[^1] != '[':
                    return 0
                else:
                    discard stack.pop()
            of '}':
                if len(stack) == 0 or stack[^1] != '{':
                    return 0
                else:
                    discard stack.pop()
            of '>':
                if len(stack) == 0 or stack[^1] != '<':
                    return 0
                else:
                    discard stack.pop()
            else:
                return 0

    if len(stack) > 0:
        var i = stack.high
        var resp = 0
        while i >= 0:
            case stack[i]:
                of '(':
                    resp = resp * 5 + 1
                of '[':
                    resp = resp * 5 + 2
                of '{':
                    resp = resp * 5 + 3
                of '<':
                    resp = resp * 5 + 4
                else:
                    return -1000
            i -= 1
        return resp

    return 0

proc process(input: seq[string]): int =
    var scores: seq[int] = @[]
    for s in input:
        let score = getScore(s)
        if score != 0:
            scores.add(score)
    scores.sort()
    return scores[scores.high div 2]

let input = getInput()
let resp = process(input)
echo(resp)

