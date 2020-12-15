package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

func parseInput() []int {
	entries := []int{}
	scanner := bufio.NewScanner(os.Stdin)
	scanner.Scan()

	rawEntry := scanner.Text()
	splits := strings.Split(rawEntry, ",")

	for _, c := range splits {
		i, _ := strconv.Atoi(c)
		entries = append(entries, i)
	}

	return entries
}

func process(numbers []int) int {
	turn := 1
	appearances := make(map[int][]int)
	lastSpoken := 0

	for _, n := range numbers {
		appearances[n] = append(appearances[n], turn)
		lastSpoken = n
		turn++
	}

	for turn <= 30000000 {
		if nAppearances, ok := appearances[lastSpoken]; ok && len(nAppearances) > 1 {
			lenN := len(nAppearances)
			laskTimeNSpoken := nAppearances[lenN-1]
			beforeLaskTimeNSpoken := nAppearances[lenN-2]

			lastSpoken = laskTimeNSpoken - beforeLaskTimeNSpoken
		} else {
			lastSpoken = 0
		}
		appearances[lastSpoken] = append(appearances[lastSpoken], turn)
		turn++
	}

	return lastSpoken
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
