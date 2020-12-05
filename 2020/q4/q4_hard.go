package main

import (
	"bufio"
	"fmt"
	"os"
	"regexp"
	"strconv"
	"strings"
)

type passport struct {
	info map[string]string
}

func (p *passport) IsValid() bool {
	return p.validateBirth() && p.validateYear() && p.validateExpiration() && p.validateHeight() && p.validateHairColor() && p.validateEyeColor() && p.validatePassportNumber()
}

func (p *passport) validateBirth() bool {
	if birth, ok := p.info["byr"]; ok {
		birthInt, _ := strconv.Atoi(birth)
		return birthInt >= 1920 && birthInt <= 2002
	}

	return false
}

func (p *passport) validateYear() bool {
	if year, ok := p.info["iyr"]; ok {
		yearInt, _ := strconv.Atoi(year)
		return yearInt >= 2010 && yearInt <= 2020
	}

	return false
}

func (p *passport) validateExpiration() bool {
	if value, ok := p.info["eyr"]; ok {
		valueInt, _ := strconv.Atoi(value)
		return valueInt >= 2020 && valueInt <= 2030
	}

	return false
}

func (p *passport) validateHeight() bool {
	if value, ok := p.info["hgt"]; ok {
		height, _ := strconv.Atoi(value[:len(value)-2])
		unit := value[len(value)-2:]
		switch unit {
		case "cm":
			{
				return height >= 150 && height <= 193

			}
		case "in":
			{
				return height >= 59 && height <= 76
			}
		}
	}

	return false
}

func (p *passport) validateHairColor() bool {
	re := regexp.MustCompile("#[0-9a-f]{1,6}")
	if value, ok := p.info["hcl"]; ok {
		return re.MatchString(value)
	}

	return false
}

func (p *passport) validateEyeColor() bool {
	validValues := map[string]bool{"amb": true, "blu": true, "brn": true, "gry": true, "grn": true, "hzl": true, "oth": true}
	if value, ok := p.info["ecl"]; ok {
		if _, ok2 := validValues[value]; ok2 {
			return true
		}
	}

	return false
}

func (p *passport) validatePassportNumber() bool {
	re := regexp.MustCompile("^\\d{9}$")
	if value, ok := p.info["pid"]; ok {
		return re.MatchString(value)
	}

	return false
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
