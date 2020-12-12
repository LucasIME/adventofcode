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

func changeDirection(dir string, angle int, state shipState) shipState {
	if dir == "R" {
		angle = 360 - angle
	}

	switch angle {
	case 0:
		return state
	case 90:
		return shipState{-state.north, state.east}
	case 180:
		return shipState{-state.east, -state.north}
	case 270:
		return shipState{state.north, -state.east}
	}

	panic("rotation with unhandled angle")
}

func processCommand(cmd command, state shipState, waypoint shipState) []shipState {
	switch cmd.direction {
	case "N":
		waypoint.north += cmd.value
	case "S":
		waypoint.north -= cmd.value
	case "E":
		waypoint.east += cmd.value
	case "W":
		waypoint.east -= cmd.value
	case "L", "R":
		waypoint = changeDirection(cmd.direction, cmd.value, waypoint)
	case "F":
		{
			state.east += cmd.value * waypoint.east
			state.north += cmd.value * waypoint.north
		}
	}

	return []shipState{state, waypoint}
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
	state := shipState{0, 0}
	waypoint := shipState{10, 1}
	for _, cmd := range commands {
		stateArray := processCommand(cmd, state, waypoint)
		state = stateArray[0]
		waypoint = stateArray[1]
	}

	return state.manhattanDistance()
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
