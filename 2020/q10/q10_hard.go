package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

func parseInput() []int {
	entries := []int{0} // Start with 0 due to ground
	scanner := bufio.NewScanner(os.Stdin)
	max := 0

	for scanner.Scan() {
		rawEntry := scanner.Text()
		i, _ := strconv.Atoi(rawEntry)
		if i > max {
			max = i
		}
		entries = append(entries, i)
	}
	entries = append(entries, max+3)

	return entries
}

func toSet(numbers []int) map[int]bool {
	resp := make(map[int]bool)
	for _, v := range numbers {
		resp[v] = true
	}
	return resp
}

func waysToGetTo(adapters map[int]bool, target int, memo map[int]int) int {
	if target < 0 {
		return 0
	}

	if target == 0 {
		return 1
	}

	if v, ok := memo[target]; ok {
		return v
	}

	candidates := []int{target - 1, target - 2, target - 3}
	total := 0
	for _, c := range candidates {
		if _, ok := adapters[c]; ok {
			total += waysToGetTo(adapters, c, memo)
		}
	}

	memo[target] = total

	return total
}

func process(numbers []int) int {
	adapterSet := toSet(numbers)
	return waysToGetTo(adapterSet, numbers[len(numbers)-1], make(map[int]int))
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
