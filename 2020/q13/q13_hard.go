package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

type busTable struct {
	timestamp int
	buses     []int
}

type busEntry struct {
	busId    int
	timeDiff int
}

func removeXFromBusList(busList []string) []busEntry {
	out := []busEntry{}
	for i, v := range busList {
		if v == "x" {
			continue
		}

		busId, _ := strconv.Atoi(v)
		out = append(out, busEntry{busId, i})
	}

	return out
}

func parseInput() []busEntry {
	scanner := bufio.NewScanner(os.Stdin)

	// Skipping first line
	scanner.Scan()

	scanner.Scan()
	rawBusList := scanner.Text()
	splitList := strings.Split(rawBusList, ",")
	busList := removeXFromBusList(splitList)

	return busList
}

func process(buses []busEntry) int {
	targetTimeCandidate := buses[0].busId
	amountToAddForNextCandidate := buses[0].busId

	for _, bus := range buses[1:] {
		for {
			if (targetTimeCandidate+bus.timeDiff)%bus.busId == 0 {
				amountToAddForNextCandidate *= bus.busId
				break
			}
			targetTimeCandidate += amountToAddForNextCandidate
		}
	}
	return targetTimeCandidate
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
