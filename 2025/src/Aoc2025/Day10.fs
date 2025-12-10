namespace Aoc2025

open System.Collections.Generic

module Day10 =
    type Buttons = int64 array
    type Machine =
        {
            desiredConfig: int64 array
            buttons: Buttons array
            costs: int64 array
        }

    let parseConfig (configStr: string) : int64 array =
        let configStrWithoutBrackets = configStr.Trim([|'['; ']'|])  
        configStrWithoutBrackets |> Seq.map (fun c -> 
            match c with
            | '.' -> 0L
            | '#' -> 1L
            | _ -> failwithf "Invalid character in config: %c" c
        ) |> Seq.toArray

    let parseSingleButton (buttonStr: string) : Buttons =
        let buttonStrWithoutParenthesis = buttonStr.Trim([|'('; ')'|])
        let rawDigits = buttonStrWithoutParenthesis.Split(',')
        rawDigits 
            |> Seq.map int64
            |> Seq.toArray

    let parseButtons (buttonsStrs: string array) : Buttons array =
        buttonsStrs
            |> Array.map parseSingleButton

    let parseCosts (costStr: string) : int64 array =
        let costStrWithoutCurly = costStr.Trim([|'{'; '}'|])  
        let rawCosts = costStrWithoutCurly.Split(',')
        rawCosts
            |> Seq.map int64
            |> Seq.toArray

    let parseLine (line: string) : Machine =
        let spacesplitr = line.Split(' ')
        let rawConfig = spacesplitr.[0]
        let rawButtons = spacesplitr.[1..spacesplitr.Length - 2]
        let rawCosts = Seq.last spacesplitr

        {
            desiredConfig = parseConfig rawConfig
            buttons = parseButtons rawButtons
            costs = parseCosts rawCosts
        }

    let parse (input: string) : Machine list =
        let rawLines = input.Split('\n')
        rawLines |> Array.map parseLine |> Array.toList

    let flipValue value = 
        match value with
            | 1L -> 0L
            | 0L -> 1L
            | _ -> failwith "unexpected value"

    let getStateAfterPress (state: int64 array, button: Buttons) : int64 array =
        let newState = Array.copy state

        button |> Array.iter (fun pos -> 
            let newValue = flipValue state.[int pos]
            newState.[int pos] <- newValue
        )

        newState

    let getNeighs (state: int64 array, buttons: Buttons array) : int64 array array =
        buttons |> Array.map (fun button -> getStateAfterPress(state, button))

    let bfs (machine: Machine) : int64 =
        let visited = HashSet<int64 array>()
        let q = Queue<int64 array * int64>()

        let startPos = machine.desiredConfig |> Array.map (fun n -> 0L)

        q.Enqueue(startPos, 0)

        let mutable resp = -1L
        let mutable finished: bool = false
        while q.Count > 0 && (not finished) do
            let pos, dist = q.Dequeue()
            if pos = machine.desiredConfig then
                resp <- dist
                finished <- true
            else
                if not (visited.Contains(pos)) then
                    visited.Add(pos) |> ignore
                    let nextStates = getNeighs(pos, machine.buttons)
                    nextStates |> Array.iter (fun nextState -> q.Enqueue(nextState, dist + 1L))

        resp

    let costToTarget (machine: Machine) : int64 =
        bfs machine

    let part1 (input: string) =
        let machines = parse input

        let distances =
            machines
            |> List.map costToTarget

        distances |> List.sum
