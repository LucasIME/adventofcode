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

func process(commands []command) int {
	curI := 0
	acc := 0
	visited := make(map[int]bool)
	for {
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

	return acc
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
