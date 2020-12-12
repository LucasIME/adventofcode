package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

type command struct {
	direction string
	value     int
}

type shipState struct {
	east            int
	north           int
	directionFacing string
}

func parseInput() []command {
	entries := []command{}
	scanner := bufio.NewScanner(os.Stdin)

	for scanner.Scan() {
		rawEntry := scanner.Text()
		i, _ := strconv.Atoi(rawEntry[1:])
		entries = append(entries, command{rawEntry[:1], i})
	}

	return entries
}

func find(v []string, x string) int {
	for i, n := range v {
		if x == n {
			return i
		}
	}
	panic("could not find element")
}

func moveForward(state shipState, value int) shipState {
	return processCommand(command{state.directionFacing, value}, state)
}

func changeDirection(dir string, angle int, state shipState) shipState {
	dirList := []string{"E", "N", "W", "S"}
	posToSkip := angle / 90
	if dir == "R" {
		posToSkip = -posToSkip
		if posToSkip < 0 {
			posToSkip += len(dirList)
		}
	}
	curPos := find(dirList, state.directionFacing)
	newPos := (curPos + posToSkip) % len(dirList)

	state.directionFacing = dirList[newPos]
	return state
}

func processCommand(cmd command, state shipState) shipState {
	switch cmd.direction {
	case "N":
		state.north += cmd.value
	case "S":
		state.north -= cmd.value
	case "E":
		state.east += cmd.value

	case "W":
		state.east -= cmd.value
	case "L", "R":
		state = changeDirection(cmd.direction, cmd.value, state)
	case "F":
		state = moveForward(state, cmd.value)
	}

	return state
}

func (s *shipState) manhattanDistance() int {
	x := s.east
	if x < 0 {
		x = -x
	}

	y := s.north
	if y < 0 {
		y = -y
	}

	return x + y
}

func process(commands []command) int {
	state := shipState{0, 0, "E"}
	for _, cmd := range commands {
		state = processCommand(cmd, state)
	}

	return state.manhattanDistance()
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
