package main

import (
	"bufio"
	"fmt"
	"os"
)

func parseInput() [][]rune {
	entries := [][]rune{}
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		rawEntry := scanner.Text()
		entries = append(entries, []rune(rawEntry))
	}

	return entries
}

func process(m [][]rune) int {
	down, right := 1, 3
	c, r := 0, 0
	total := 0
	for r < len(m) {
		if m[r][c] == '#' {
			total += 1
		}
		r += down
		c = (c + right) % len(m[c])
	}

	return total
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
