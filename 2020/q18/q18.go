package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

func parseInput() []string {
	scanner := bufio.NewScanner(os.Stdin)
	entries := []string{}

	for scanner.Scan() {
		rawEntry := scanner.Text()
		entries = append(entries, rawEntry)
	}

	return entries
}

func isOperator(c rune) bool {
	return c == '*' || c == '+'
}

func applyOperator(c rune, a, b int) int {
	switch c {
	case '*':
		return a * b
	case '+':
		return a + b
	}

	panic("Unknown operator")
}

func parseExpression(s string) int {
	last := -1
	op := ' '
	i := 0
	for i < len(s) {
		c := []rune(s)[i]
		if c == ' ' || c == ')' {
			i++
			continue
		} else if c == '(' {
			expEnd := 0
			level := 1
			for i2 := i + 1; i2 < len(s); i2++ {
				if s[i2] == '(' {
					level++
				} else if s[i2] == ')' {
					level--
					if level == 0 {
						expEnd = i2
						break
					}
				}
			}

			v := parseExpression(s[i+1 : expEnd])

			if last != -1 {
				arg1 := last
				final := applyOperator(op, arg1, v)
				last = final
			} else {
				last = v
			}
			i = expEnd
		} else if isOperator(c) {
			op = c
		} else {
			v, _ := strconv.Atoi(string(c))
			if last != -1 {
				arg1 := last
				final := applyOperator(op, arg1, v)
				last = final
			} else {
				last = v
			}
		}
		i++
	}
	return last
}

func process(expressions []string) int {
	total := 0
	for _, exp := range expressions {
		total += parseExpression(exp)
	}

	return total
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
