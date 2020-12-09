package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

func parseInput() []int {
	entries := []int{}
	scanner := bufio.NewScanner(os.Stdin)

	for scanner.Scan() {
		rawEntry := scanner.Text()
		i, _ := strconv.Atoi(rawEntry)
		entries = append(entries, i)
	}

	return entries
}

func isValidEntry(numbers []int, curIndex int, preamble int) bool {
	numbersToConsider := numbers[curIndex-preamble : curIndex]
	allSums := make(map[int]bool)

	for i, v := range numbersToConsider {
		for j := i + 1; j < len(numbersToConsider); j++ {
			allSums[v+numbersToConsider[j]] = true
		}
	}

	_, ok := allSums[numbers[curIndex]]

	return ok
}

func process(numbers []int) int {
	preamble := 25
	for i := preamble; i < len(numbers); i++ {
		if !isValidEntry(numbers, i, preamble) {
			return numbers[i]
		}
	}

	panic("Could not find number")
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
