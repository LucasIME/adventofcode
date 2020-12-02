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

func nthRune(s string, pos int) rune {
	return ([]rune(s))[pos]
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
		letter := nthRune(splits[1], 0)
		password := splits[2]

		passEntry := passwordEntry{repRange, letter, password}

		entries = append(entries, passEntry)
	}

	return entries
}

func isValidEntry(entry passwordEntry) bool {
	index1 := entry.repetitionRange[0] - 1
	index2 := entry.repetitionRange[1] - 1

	return (nthRune(entry.password, index1) == entry.char) != (nthRune(entry.password, index2) == entry.char)
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
