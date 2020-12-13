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

func removeXFromBusList(busList []string) []int {
	out := []int{}
	for _, v := range busList {
		if v == "x" {
			continue
		}

		i, _ := strconv.Atoi(v)
		out = append(out, i)
	}

	return out
}

func parseInput() busTable {
	scanner := bufio.NewScanner(os.Stdin)

	scanner.Scan()
	rawTimestamp := scanner.Text()
	time, _ := strconv.Atoi(rawTimestamp)

	scanner.Scan()
	rawBusList := scanner.Text()
	splitList := strings.Split(rawBusList, ",")
	busList := removeXFromBusList(splitList)

	return busTable{time, busList}
}

func process(table busTable) int {
	busId := 0
	minutesToWait := 10000000
	for _, v := range table.buses {
		remainder := table.timestamp % v
		waitTime := v - remainder
		if remainder == 0 {
			waitTime = 0
		}

		if waitTime < minutesToWait {
			minutesToWait = waitTime
			busId = v
		}
	}

	return busId * minutesToWait
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
