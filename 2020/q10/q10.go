package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
	"strconv"
)

func parseInput() []int {
	entries := []int{0} // Start with 0 due to ground
	scanner := bufio.NewScanner(os.Stdin)

	for scanner.Scan() {
		rawEntry := scanner.Text()
		i, _ := strconv.Atoi(rawEntry)
		entries = append(entries, i)
	}
	sort.Ints(entries)

	return entries
}

func process(numbers []int) int {
	oneDiff := 0
	threeDiff := 1 // start with 1 due to final device

	for i := 1; i < len(numbers); i++ {
		cur := numbers[i]
		last := numbers[i-1]
		diff := cur - last
		if diff == 1 {
			oneDiff++
		} else if diff == 3 {
			threeDiff++
		}
	}

	return oneDiff * threeDiff
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
