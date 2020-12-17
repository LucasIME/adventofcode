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

func isPotentiallyValid(ticket []int, rules ticketRules) bool {
	for _, v := range ticket {
		if !isValid(v, rules) {
			return false
		}
	}

	return true
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

func isValidSingleRangeSet(n int, ranges []allowedRange) bool {
	for _, r := range ranges {
		if n >= r.lower && n <= r.upper {
			return true
		}
	}

	return false
}

func filterToValidTickets(tickets [][]int, rules ticketRules) [][]int {
	resp := [][]int{}
	for _, ticket := range tickets {
		if isPotentiallyValid(ticket, rules) {
			resp = append(resp, ticket)
		}
	}

	return resp
}

func findFieldsOrder(nearby [][]int, rules ticketRules) []string {
	allFields := []string{} // len(rules.rules))
	for k := range rules.rules {
		allFields = append(allFields, k)
	}

	//fieldToValidPositions := make(map[string][]int)
	positionToValidFields := make([]map[string]bool, len(allFields))
	for i := range allFields {
		positionToValidFields[i] = copyArrayToSet(allFields)
	}

	fmt.Println("here", positionToValidFields)

	for _, neigh := range nearby {
		for i, v := range neigh {
			for _, field := range allFields {
				fieldRanges := rules.rules[field]
				if !isValidSingleRangeSet(v, fieldRanges) {
					delete(positionToValidFields[i], field)
				}
			}
		}
	}

	fmt.Println(positionToValidFields)

	indexAlreadyProcessed := make(map[int]bool)
	i := 0
	for i < len(positionToValidFields) {
		fields := positionToValidFields[i]
		if _, ok := indexAlreadyProcessed[i]; !ok && len(fields) == 1 {
			indexAlreadyProcessed[i] = true
			fieldToDeleteElsewhere := "temp"
			for k := range fields {
				fieldToDeleteElsewhere = k
			}
			for j := 0; j < len(positionToValidFields); j++ {
				if j != i {
					delete(positionToValidFields[j], fieldToDeleteElsewhere)
				}
			}
			i = 0
		} else {
			i++
		}
	}

	fmt.Println(positionToValidFields)
	resp := make([]string, len(allFields))
	for i, m := range positionToValidFields {
		for k := range m {
			resp[i] = k
		}
	}

	return resp
}

func copyArrayToSet(array []string) map[string]bool {
	c := make(map[string]bool)
	for _, v := range array {
		c[v] = true
	}

	return c
}

func process(rules ticketRules) int {
	filteredNearby := filterToValidTickets(rules.nearbyTickets, rules)
	fmt.Println(filteredNearby)
	fieldOrder := findFieldsOrder(filteredNearby, rules)

	resp := 1
	for i, field := range fieldOrder {
		if strings.HasPrefix(field, "departure") {
			resp *= rules.ticket[i]
		}
	}

	return resp
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
