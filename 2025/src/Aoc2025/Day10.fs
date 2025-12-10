namespace Aoc2025

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
        rawDigits |> Seq.map (fun c -> int64 c - int64 '0') |> Seq.toArray

    let parseButtons (buttonsStrs: string array) : Buttons array =
        buttonsStrs
            |> Array.map parseSingleButton

    let parseCosts (costStr: string) : int64 array =
        let costStrWithoutCurly = costStr.Trim([|'{'; '}'|])  
        costStrWithoutCurly |> Seq.map (fun c -> int64 c - int64 '0') |> Seq.toArray

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

    let part1 (input: string) =
        let machines = parse input
        printfn "%A" machines
        10L
