import sequtils
import strutils
import tables

type
    Game = ref object
        inputs: seq[int]
        boards: seq[seq[seq[int]]]
        takenPositions: seq[seq[(int, int)]]

proc toBoard(input: seq[string]): seq[seq[int]] =
    return input.mapIt((it.splitWhitespace).map(parseInt))

proc getInput(): Game =
    let rawInp = readAll(stdin).strip.split("\n\n")
    let entries = rawInp[0].split(',').map(parseInt)


    let boards = rawInp[1..<rawInp.len].map(splitLines).map(toBoard)

    return  Game(inputs: entries, boards: boards, takenPositions: newSeq[seq[(int, int)]](boards.len))

proc playOneRound(game: Game, entry:int): Game =
    for boardNum in 0..<game.boards.len:
        let board = game.boards[boardNum]
        for row in 0..4:
            for col in 0..4:
                if board[row][col] == entry:
                    if game.takenPositions[boardNum] == nil:
                        game.takenPositions[boardNum] = @[]
                    game.takenPositions[boardNum].add((row, col))
    return game

proc isRowCompleted(row: int, takenPos: seq[(int, int)]): bool =
    if takenPos == nil:
        return false

    for col in 0..4:
        if (row, col) notin takenPos:
            return false
    return true


proc isColCompleted(col: int, takenPos: seq[(int, int)]): bool =
    if takenPos == nil:
        return false

    for row in 0..4:
        if (row, col) notin takenPos:
            return false
    return true

proc isBoardFinished(board: seq[(int,int)]): bool =
    for x in 0..4:
        if isRowCompleted(x, board):
            return true
        if isColCompleted(x, board):
            return true
    return false


proc tryGetWinner(game: Game): int =
    for i in 0..<game.boards.len:
        if isBoardFinished(game.takenPositions[i]):
            return i
    return -1

proc getPoints(game: Game, winner: int): int =
    var resp = 0
    for row in 0..4:
        for col in 0..4:
            if (row,col) notin  game.takenPositions[winner]:
                resp += game.boards[winner][row][col]
    return resp

proc process(game: Game): int =
    var currentGame = game

    var winner : int
    var lastNum: int

    for entry in game.inputs:
        lastNum = entry
        currentGame = playOneRound(currentGame, entry)
        let maybeWinner = tryGetWinner(currentGame)
        if maybeWinner != -1:
            winner = maybeWinner
            break

    return lastNum * getPoints(game, winner)

let input = getInput()
let resp = process(input)
echo(resp)
