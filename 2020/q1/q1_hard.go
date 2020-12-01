package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

func parseInput() []int {
	lines := []int{}
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		i, _ := strconv.Atoi(scanner.Text())
		lines = append(lines, i)
	}

	return lines
}

func process(entries []int) int {
	entrieSet := make(map[int]bool)
	for _, entry := range entries {
		entrieSet[entry] = true
	}

	var resp int

	for i, value1 := range entries {
		for j := i + 1; j < len(entries); j++ {
			value2 := entries[j]
			value3 := 2020 - value2 - value1
			if _, ok := entrieSet[value3]; ok {
				resp = value1 * value2 * value3
				return resp
			}
		}
	}

	panic("Couldn't find a solution")
}

func main() {
	entries := parseInput()
	resp := process(entries)

	fmt.Println(resp)
}
