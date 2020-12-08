package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

type command struct {
	cmd    string
	target int
}

type terminationValue struct {
	acc                  int
	terminatedGracefully bool
}

func parseInput() []command {
	entries := []command{}
	scanner := bufio.NewScanner(os.Stdin)

	for scanner.Scan() {
		rawEntry := scanner.Text()
		s := strings.Split(rawEntry, " ")
		target, _ := strconv.Atoi(s[1])
		entries = append(entries, command{s[0], target})
	}

	return entries
}

func processCommands(commands []command) terminationValue {
	curI := 0
	acc := 0
	visited := make(map[int]bool)
	for {
		if curI == len(commands) {
			break
		}
		if _, ok := visited[curI]; ok {
			break
		}
		visited[curI] = true
		command := commands[curI]
		op := command.cmd
		switch op {
		case "nop":
			{
				curI += 1
				continue
			}
		case "acc":
			{
				acc += command.target
				curI += 1
			}
		case "jmp":
			{
				curI += command.target
			}
		}
	}

	return terminationValue{acc, curI == len(commands)}
}

func arrayCopy(array []command) []command {
	resp := []command{}
	for _, v := range array {
		resp = append(resp, v)
	}

	return resp
}

func generateAllPossibleProgramVariations(commands []command) [][]command {
	resp := [][]command{commands}
	for i, c := range commands {
		newArray := arrayCopy(commands)
		if c.cmd == "nop" {
			newCmd := command{"jmp", c.target}
			newArray[i] = newCmd
			resp = append(resp, newArray)
		} else if c.cmd == "jmp" {
			newCmd := command{"nop", c.target}
			newArray[i] = newCmd
			resp = append(resp, newArray)
		}
	}

	return resp
}

func process(commands []command) int {
	allProgs := generateAllPossibleProgramVariations(commands)
	for _, prog := range allProgs {
		result := processCommands(prog)
		if result.terminatedGracefully {
			return result.acc
		}
	}
	panic("Could not find program that would terminate gracefully")
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
