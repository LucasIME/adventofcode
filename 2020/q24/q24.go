package main

import (
	"bufio"
	"fmt"
	"os"
)

const (
	E  = "e"
	SE = "se"
	SW = "sw"
	W  = "w"
	NW = "nw"
	NE = "ne"
)

func parseLine(s string) []string {
	i := 0
	out := []string{}

	for i < len(s) {
		c := []rune(s)[i]
		if c == 'e' || c == 'w' {
			out = append(out, s[i:i+1])
			i++
		} else if c == 's' || c == 'n' {
			out = append(out, s[i:i+2])
			i += 2
		}
	}

	return out
}

func getCoordAfterSteps(steps []string) (int, int, int) {
	x := 0
	y := 0
	z := 0

	for _, dir := range steps {
		switch dir {
		case E:
			x++
			y--
		case SE:
			z++
			y--
		case SW:
			x--
			z++
		case W:
			y++
			x--
		case NW:
			y++
			z--
		case NE:
			x++
			z--
		}
	}

	return x, y, z
}

func parseInput() [][]string {
	scanner := bufio.NewScanner(os.Stdin)
	entries := [][]string{}

	for scanner.Scan() {
		rawEntry := scanner.Text()
		e := parseLine(rawEntry)
		entries = append(entries, e)
	}

	return entries
}

func process(entries [][]string) int {
	blackCoords := make(map[string]bool)

	for _, path := range entries {
		destX, destY, destZ := getCoordAfterSteps(path)
		arrayKey := []int{destX, destY, destZ}
		key := fmt.Sprint(arrayKey)

		if _, ok := blackCoords[key]; ok {
			delete(blackCoords, key)
		} else {
			blackCoords[key] = true
		}
	}

	return len(blackCoords)
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
