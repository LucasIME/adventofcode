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

func findRangeSum(numbers []int, target int) []int {
	start := 0

	for start < len(numbers) {
		curSum := numbers[start]
		end := start + 1
		for end < len(numbers) {
			curSum += numbers[end]
			if curSum < target {
				end += 1
			} else if curSum == target {
				return numbers[start : end+1]
			} else {
				break
			}
		}
		start += 1
	}

	panic("could not find range")
}

func getMin(v []int) int {
	var mini int
	for i, v := range v {
		if i == 0 || v < mini {
			mini = v
		}
	}

	return mini
}

func getMax(v []int) int {
	var max int
	for i, v := range v {
		if i == 0 || v > max {
			max = v
		}
	}

	return max
}

func process(numbers []int) int {
	targetSum := 36845998
	r := findRangeSum(numbers, targetSum)

	return getMin(r) + getMax(r)
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
