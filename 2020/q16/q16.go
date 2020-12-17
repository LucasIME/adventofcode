package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

const (
	MASK = "mask"
	MEM  = "mem"
)

type ticketRules struct {
	rules         map[string][]allowedRange
	ticket        []int
	nearbyTickets [][]int
}

type allowedRange struct {
	lower int
	upper int
}

func parseInput() ticketRules {
	scanner := bufio.NewScanner(os.Stdin)
	parsingNow := "ranges"
	ranges := make(map[string][]allowedRange)
	ticket := []int{}
	nearby := [][]int{}

	for scanner.Scan() {
		rawEntry := scanner.Text()

		switch parsingNow {

		case "ranges":
			{
				if rawEntry == "" {
					parsingNow = "ticket"
					break
				}
				curRanges := []allowedRange{}
				splits := strings.Split(rawEntry, ": ")
				vList := strings.Split(splits[1], " or ")
				for _, rawRange := range vList {
					rawRangeSplit := strings.Split(rawRange, "-")
					lower, _ := strconv.Atoi(rawRangeSplit[0])
					upper, _ := strconv.Atoi(rawRangeSplit[1])
					curRanges = append(curRanges, allowedRange{lower, upper})
				}

				ranges[splits[0]] = curRanges
			}
		case "ticket":
			{
				if rawEntry == "" {
					parsingNow = "nearby"
					break
				}
				if rawEntry == "your ticket:" {
					break
				}
				splits := strings.Split(rawEntry, ",")
				intV := make([]int, len(splits))
				for i, rawV := range splits {
					convertedV, _ := strconv.Atoi(rawV)
					intV[i] = convertedV
				}
				ticket = intV
			}

		case "nearby":
			{
				if rawEntry == "nearby tickets:" {
					break
				}
				splits := strings.Split(rawEntry, ",")
				intV := make([]int, len(splits))
				for i, rawV := range splits {
					convertedV, _ := strconv.Atoi(rawV)
					intV[i] = convertedV
				}
				nearby = append(nearby, intV)
			}
		}
	}

	return ticketRules{ranges, ticket, nearby}
}

func isValid(n int, rules ticketRules) bool {
	for _, ranges := range rules.rules {
		for _, r := range ranges {
			if n >= r.lower && n <= r.upper {
				return true
			}
		}
	}
	return false
}

func process(rules ticketRules) int {
	total := 0
	for _, neigh := range rules.nearbyTickets {
		for _, v := range neigh {
			if !isValid(v, rules) {
				total += v
			}
		}
	}

	return total
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
