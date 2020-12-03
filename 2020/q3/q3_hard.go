package main

import (
	"bufio"
	"fmt"
	"os"
)

type slope struct {
	right int
	down  int
}

func parseInput() [][]rune {
	entries := [][]rune{}
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		rawEntry := scanner.Text()
		entries = append(entries, []rune(rawEntry))
	}

	return entries
}

func getTreesForSlope(m [][]rune, s slope) int {
	c, r := 0, 0
	total := 0
	for r < len(m) {
		if m[r][c] == '#' {
			total += 1
		}
		r += s.down
		c = (c + s.right) % len(m[c])
	}

	return total
}

func process(m [][]rune) int {
	slopes := []slope{
		{1, 1},
		{3, 1},
		{5, 1},
		{7, 1},
		{1, 2},
	}

	total := 1
	for _, slope := range slopes {
		trees := getTreesForSlope(m, slope)
		total *= trees
	}

	return total
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
