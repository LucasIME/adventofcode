package main

import (
	"bufio"
	"fmt"
	"os"
)

type groupAnswers struct {
	answers []string
}

func parseInput() []groupAnswers {
	entries := []groupAnswers{}
	scanner := bufio.NewScanner(os.Stdin)

	curGroupAnswer := []string{}
	for scanner.Scan() {
		rawEntry := scanner.Text()
		if rawEntry == "" {
			entries = append(entries, groupAnswers{curGroupAnswer})
			curGroupAnswer = []string{}
		} else {
			curGroupAnswer = append(curGroupAnswer, rawEntry)
		}
	}

	return entries
}

func commonAnswersInGroup(groupAnswer groupAnswers) int {
	answerCountMap := make(map[rune]int)
	numberOfPeopleInGroup := len(groupAnswer.answers)
	for _, answer := range groupAnswer.answers {
		for _, letter := range answer {
			if _, ok := answerCountMap[letter]; ok {
				answerCountMap[letter] += 1
			} else {
				answerCountMap[letter] = 1
			}
		}
	}

	resp := 0
	for _, count := range answerCountMap {
		if count == numberOfPeopleInGroup {
			resp += 1
		}
	}
	return resp
}

func process(groupAnswers []groupAnswers) int {
	total := 0
	for _, groupAnswer := range groupAnswers {
		total += commonAnswersInGroup(groupAnswer)
	}

	return total
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
