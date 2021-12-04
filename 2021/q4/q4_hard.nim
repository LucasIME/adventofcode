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


proc tryGetNotWinner(game: Game): int =
    var nonFinishedBoard = -1
    for i in 0..<game.boards.len:
        if not isBoardFinished(game.takenPositions[i]):
            if nonFinishedBoard == -1:
                nonFinishedBoard = i
            else:
                return -1
    return nonFinishedBoard

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

    for i in 0..<game.inputs.len:
        let entry = game.inputs[i]
        lastNum = entry
        currentGame = playOneRound(currentGame, entry)
        let lastWinner = tryGetNotWinner(currentGame)
        if lastWinner != -1:
            winner = lastWinner
            for x in game.inputs[i+1..<game.inputs.len]:
                currentGame = playOneRound(currentGame, x)
                if isBoardFinished(game.takenPositions[winner]):
                    return x * getPoints(game, winner)

    return -1

let input = getInput()
let resp = process(input)
echo(resp)
