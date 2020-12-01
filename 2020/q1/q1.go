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
	for i := range entrieSet {
		counterPart := 2020 - i
		if _, ok := entrieSet[counterPart]; ok {
			resp = i * counterPart
			break
		}
	}

	return resp
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
