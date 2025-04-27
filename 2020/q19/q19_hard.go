package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

type Rule struct {
	ruleNum int
	char    rune
	rules   [][]int
}

type Entry struct {
	rules    []Rule
	messages []string
}

func (r *Rule) isCharRule() bool {
	return r.char != ' '
}

type Input struct {
	ruleNum int
	s       string
}

type Result struct {
	matches bool
	size    int
}

func matches(r Rule, m string, e Entry, mem map[Input]Result) (bool, int) {
	if r.isCharRule() {
		return len(m) >= 1 && []rune(m)[0] == r.char, 1
	}

	input := Input{r.ruleNum, m}
	if cached, ok := mem[input]; ok {
		return cached.matches, cached.size
	}

	if r.ruleNum == 0 {
		for i := 1; i < len(m); i++ {
			prefix := m[:i]
			suffix := m[i:]

			prefixMatches, prefixSize := matches(e.rules[8], prefix, e, mem)
			suffixMatches, suffixSize := matches(e.rules[11], suffix, e, mem)

			if prefixMatches && suffixMatches && prefixSize == len(prefix) && suffixSize == len(suffix) {
				mem[input] = Result{true, prefixSize + suffixSize}
				return true, prefixSize + suffixSize
			}
		}

		return false, -1
	}

	if r.ruleNum == 8 {
		matches42, matches42Size := matches(e.rules[42], m, e, mem)
		if matches42 && matches42Size == len(m) {
			mem[input] = Result{true, matches42Size}
			return true, matches42Size
		}

		for i := 1; i < len(m); i++ {
			prefix := m[:i]
			suffix := m[i:]

			prefixMatches, prefixSize := matches(e.rules[42], prefix, e, mem)
			suffixMatches, suffixSize := matches(e.rules[8], suffix, e, mem)

			if prefixMatches && suffixMatches && prefixSize == len(prefix) && suffixSize == len(suffix) {
				mem[input] = Result{true, prefixSize + suffixSize}
				return true, prefixSize + suffixSize
			}
		}

		mem[input] = Result{false, -1}

		return false, -1
	}

	if r.ruleNum == 11 {
		for i := 1; i <= len(m)-1; i++ {
			prefix := m[:i]
			suffix := m[i:]

			prefixMatches, prefixSize := matches(e.rules[42], prefix, e, mem)
			suffixMatches, suffixSize := matches(e.rules[31], suffix, e, mem)

			if prefixMatches && suffixMatches && prefixSize == len(prefix) && suffixSize == len(suffix) {
				mem[input] = Result{true, prefixSize + suffixSize}
				return true, prefixSize + suffixSize
			}
		}

		for i := 1; i <= len(m)-2; i++ {
			for j := i + 1; j <= len(m)-1; j++ {
				prefix := m[:i]
				mid := m[i:j]
				suffix := m[j:]

				prefixMatches, prefixSize := matches(e.rules[42], prefix, e, mem)
				midMatches, midSize := matches(e.rules[11], mid, e, mem)
				suffixMatches, suffixSize := matches(e.rules[31], suffix, e, mem)

				if prefixMatches && midMatches && suffixMatches && prefixSize == len(prefix) && midSize == len(mid) && suffixSize == len(suffix) {
					mem[input] = Result{true, prefixSize + midSize + suffixSize}
					return true, prefixSize + midSize + suffixSize
				}
			}
		}

		mem[input] = Result{false, -1}
		return false, -1
	}

	for _, ruleSet := range r.rules {
		startPos := 0
		doesMatch := true
		for _, ruleNum := range ruleSet {
			ok, size := matches(e.rules[ruleNum], m[startPos:], e, mem)
			if ok {
				startPos += size
			} else {
				doesMatch = false
				break
			}
		}

		if doesMatch {
			mem[input] = Result{true, startPos}
			return true, startPos
		}
	}

	mem[input] = Result{false, -1}
	return false, -1
}

func parseInput() Entry {
	scanner := bufio.NewScanner(os.Stdin)
	entries := Entry{make([]Rule, 131), []string{}}
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
			rawRightSide := splits[1]

			if strings.Contains(rawRightSide, "\"") {
				literal := []rune(rawRightSide)[1]
				entries.rules[ruleNum] = Rule{ruleNum, literal, [][]int{}}
			} else {
				r := Rule{ruleNum, ' ', [][]int{}}
				rawOptions := strings.Split(rawRightSide, " | ")
				for _, rawOption := range rawOptions {
					indexes := []int{}
					rawIndexes := strings.Split(rawOption, " ")
					for _, rawIndex := range rawIndexes {
						intV, _ := strconv.Atoi(rawIndex)
						indexes = append(indexes, intV)
					}
					r.rules = append(r.rules, indexes)
				}

				entries.rules[ruleNum] = r
			}

		} else {
			entries.messages = append(entries.messages, rawEntry)
		}
	}

	return entries
}

func process(entries Entry) int {
	total := 0
	for _, m := range entries.messages {
		if ok, size := matches(entries.rules[0], m, entries, make(map[Input]Result)); ok {
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
