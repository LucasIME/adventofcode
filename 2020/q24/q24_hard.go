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

func getNextPos(x, y, z int, dir string) (int, int, int) {
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

	return x, y, z
}

func getNeighs(pos [3]int) [][3]int {
	out := [][3]int{}
	x, y, z := pos[0], pos[1], pos[2]
	for _, dir := range []string{E, SE, SW, W, NW, NE} {
		nX, nY, nZ := getNextPos(x, y, z, dir)
		out = append(out, [3]int{nX, nY, nZ})
	}

	return out
}

func getCoordAfterSteps(steps []string) (int, int, int) {
	x := 0
	y := 0
	z := 0

	for _, dir := range steps {
		x, y, z = getNextPos(x, y, z, dir)
	}

	return x, y, z
}

func updateDay(blackCoords map[[3]int]bool) map[[3]int]bool {
	newDayBlackCoords := make(map[[3]int]bool)
	for k, v := range blackCoords {
		newDayBlackCoords[k] = v
	}

	whiteNeighs := [][3]int{}

	for blackCoord := range blackCoords {
		neighs := getNeighs(blackCoord)
		blackNeighs := 0
		for _, n := range neighs {
			if _, ok := blackCoords[n]; ok {
				blackNeighs++
			} else {
				whiteNeighs = append(whiteNeighs, n)
			}
		}

		if blackNeighs == 0 || blackNeighs > 2 {
			delete(newDayBlackCoords, blackCoord)
		}
	}

	for _, whiteCoord := range whiteNeighs {
		neighs := getNeighs(whiteCoord)
		blackNeighs := 0
		for _, n := range neighs {
			if _, ok := blackCoords[n]; ok {
				blackNeighs++
			}
		}

		if blackNeighs == 2 {
			newDayBlackCoords[whiteCoord] = true
		}
	}

	return newDayBlackCoords
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
	blackCoords := make(map[[3]int]bool)

	for _, path := range entries {
		destX, destY, destZ := getCoordAfterSteps(path)
		arrayKey := [3]int{destX, destY, destZ}

		if _, ok := blackCoords[arrayKey]; ok {
			delete(blackCoords, arrayKey)
		} else {
			blackCoords[arrayKey] = true
		}
	}

	for day := 1; day <= 100; day++ {
		blackCoords = updateDay(blackCoords)
	}

	return len(blackCoords)
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
