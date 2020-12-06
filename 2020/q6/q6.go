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

func distinctAnswersInGroup(groupAnswer groupAnswers) int {
	answerSet := make(map[rune]bool)
	for _, answer := range groupAnswer.answers {
		for _, letter := range answer {
			answerSet[letter] = true
		}
	}
	return len(answerSet)
}

func process(groupAnswers []groupAnswers) int {
	total := 0
	for _, groupAnswer := range groupAnswers {
		total += distinctAnswersInGroup(groupAnswer)
	}

	return total
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
