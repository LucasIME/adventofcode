package main

import (
	"bufio"
	"fmt"
	"os"
)

func getMax(a, b int) int {
	if a >= b {
		return a
	}

	return b
}

func getSeatId(seat string) int {
	rowRegion := seat[:7]
	colRegion := seat[7:]

	lowRow := 0
	highRow := 127
	for _, v := range rowRegion {
		switch v {
		case 'B':
			lowRow = ((highRow + lowRow) / 2) + 1
		case 'F':
			highRow = (highRow + lowRow) / 2
		}
	}

	lowCol := 0
	highCol := 7

	for _, v := range colRegion {
		switch v {
		case 'R':
			lowCol = ((highCol + lowCol) / 2) + 1
		case 'L':
			highCol = (highCol + lowCol) / 2
		}
	}

	return lowRow*8 + lowCol
}

func parseInput() []string {
	entries := []string{}
	scanner := bufio.NewScanner(os.Stdin)

	for scanner.Scan() {
		rawEntry := scanner.Text()
		entries = append(entries, rawEntry)
	}

	return entries
}

func process(seats []string) int {
	maxId := 0
	for _, s := range seats {
		maxId = getMax(maxId, getSeatId(s))
	}

	return maxId
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
