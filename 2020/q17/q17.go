package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

type space struct {
	points map[string]bool
}

func toKey(pos []int) string {
	return fmt.Sprintf("%d,%d,%d", pos[0], pos[1], pos[2])
}

func toPos(s string) []int {
	r := make([]int, 3)
	splits := strings.Split(s, ",")

	for i, c := range splits {
		v, _ := strconv.Atoi(c)
		r[i] = v
	}

	return r
}

func to3dMap(twoSpace [][]rune) space {
	s := space{make(map[string]bool)}

	for i, row := range twoSpace {
		for j, r := range row {
			if r == '#' {
				s.points[toKey([]int{i, j, 0})] = true
			}
		}
	}

	return s
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

func getNeighs(point []int) [][]int {
	resp := [][]int{}
	for i := point[0] - 1; i <= point[0]+1; i++ {
		for j := point[1] - 1; j <= point[1]+1; j++ {
			for k := point[2] - 1; k <= point[2]+1; k++ {
				if i != point[0] || j != point[1] || k != point[2] {
					resp = append(resp, []int{i, j, k})
				}
			}
		}
	}

	return resp
}

func nextSpace(s space) space {
	newSpace := space{make(map[string]bool)}
	neighCount := make(map[string]int)
	for k := range s.points {
		neighs := getNeighs(toPos(k))
		for _, n := range neighs {
			neighCount[toKey(n)]++
		}
	}

	for k, c := range neighCount {
		if s.points[k] && (c == 2 || c == 3) {
			newSpace.points[k] = true
		} else if s.points[k] == false && c == 3 {
			newSpace.points[k] = true
		}
	}

	return newSpace
}

func process(entry [][]rune) int {
	s := to3dMap(entry)
	for i := 0; i < 6; i++ {
		s = nextSpace(s)
	}

	return len(s.points)
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
