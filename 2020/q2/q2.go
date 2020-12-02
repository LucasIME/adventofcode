package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

type passwordEntry struct {
	repetitionRange []int
	char            rune
	password        string
}

func firstRune(s string) rune {
	return ([]rune(s))[0]
}

func convertStringArrayToIntArray(stringArray []string) []int {
	resp := []int{}
	for _, value := range stringArray {
		intValue, _ := strconv.Atoi(value)
		resp = append(resp, intValue)
	}

	return resp
}

func parseInput() []passwordEntry {
	entries := []passwordEntry{}
	scanner := bufio.NewScanner(os.Stdin)
	for scanner.Scan() {
		rawEntry := scanner.Text()
		splits := strings.Split(rawEntry, " ")
		repRange := convertStringArrayToIntArray(strings.Split(splits[0], "-"))
		letter := firstRune(splits[1])
		password := splits[2]

		passEntry := passwordEntry{repRange, letter, password}

		entries = append(entries, passEntry)
	}

	return entries
}

func isValidEntry(entry passwordEntry) bool {
	minAppearance := entry.repetitionRange[0]
	maxAppearance := entry.repetitionRange[1]

	totalCount := 0
	for _, letter := range entry.password {
		if letter == entry.char {
			totalCount += 1
		}
	}

	return totalCount >= minAppearance && totalCount <= maxAppearance
}

func process(entries []passwordEntry) int {
	resp := 0
	for _, entry := range entries {
		if isValidEntry(entry) {
			resp += 1
		}
	}

	return resp
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
