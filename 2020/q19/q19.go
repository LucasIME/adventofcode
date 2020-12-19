package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

type rule struct {
	val      rune
	subRules [][]int
}

type entry struct {
	rules    []rule
	messages []string
}

func (r *rule) isCharRule() bool {
	return r.val != 32
}

func matches(r rule, m string, e entry) (bool, int) {
	if r.isCharRule() {
		return len(m) >= 1 && []rune(m)[0] == r.val, 1
	}

	for _, ruleSet := range r.subRules {
		startPos := 0
		doesMatch := true
		for _, ruleNum := range ruleSet {
			ok, size := matches(e.rules[ruleNum], m[startPos:], e)
			if ok {
				startPos += size
			} else {
				doesMatch = false
				break
			}
		}

		if doesMatch {
			return true, startPos
		}
	}

	return false, -1
}

func parseInput() entry {
	scanner := bufio.NewScanner(os.Stdin)
	entries := entry{make([]rule, 131), []string{}}
	mode := "rules"

	for scanner.Scan() {
		rawEntry := scanner.Text()

		if mode == "rules" {
			if rawEntry == "" {
				mode = "nums"
				continue
			}

			splits := strings.Split(rawEntry, ": ")
			ruleNum, _ := strconv.Atoi(splits[0])

			if strings.Contains(splits[1], "\"") {
				entries.rules[ruleNum] = rule{[]rune(splits[1])[1], [][]int{}}
			} else {
				r := rule{' ', [][]int{}}
				options := strings.Split(splits[1], " | ")
				for _, s := range options {
					indexes := []int{}
					rawIndexes := strings.Split(s, " ")
					for _, rawIndex := range rawIndexes {
						intV, _ := strconv.Atoi(rawIndex)
						indexes = append(indexes, intV)
					}
					r.subRules = append(r.subRules, indexes)
				}

				entries.rules[ruleNum] = r
			}

		} else {
			entries.messages = append(entries.messages, rawEntry)
		}
	}

	return entries
}

func process(entries entry) int {
	total := 0
	for _, m := range entries.messages {
		if ok, size := matches(entries.rules[0], m, entries); ok {
			if ok && size == len(m) {
				total++
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
