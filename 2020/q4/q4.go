package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

type passport struct {
	info map[string]string
}

func (p *passport) IsValid() bool {
	mandatoryFields := []string{
		"byr",
		"iyr",
		"eyr",
		"hgt",
		"hcl",
		"ecl",
		"pid",
	}

	for _, field := range mandatoryFields {
		if _, ok := p.info[field]; !ok {
			return false
		}
	}

	return true
}

func parseInput() []passport {
	entries := []passport{}
	scanner := bufio.NewScanner(os.Stdin)

	curPassport := make(map[string]string)
	for scanner.Scan() {
		rawEntry := scanner.Text()
		if rawEntry == "" {
			entries = append(entries, passport{curPassport})
			curPassport = make(map[string]string)
		} else {
			lineValues := strings.Split(rawEntry, " ")
			for _, rawValuePair := range lineValues {
				kvRaw := strings.Split(rawValuePair, ":")
				key := kvRaw[0]
				value := kvRaw[1]

				curPassport[key] = value
			}
		}
	}

	return entries
}

func process(passports []passport) int {
	total := 0
	for _, p := range passports {
		if p.IsValid() {
			total += 1
		}
	}

	return total
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
