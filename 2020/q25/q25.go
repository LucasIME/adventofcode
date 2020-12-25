package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

const DIV = 20201227
const SUBJECT = 7

func parseInput() (int, int) {
	scanner := bufio.NewScanner(os.Stdin)

	cardPublic := -1
	scanner.Scan()
	rawEntry := scanner.Text()
	v, _ := strconv.Atoi(rawEntry)
	cardPublic = v

	doorPublic := -1
	scanner.Scan()
	rawEntry2 := scanner.Text()
	v2, _ := strconv.Atoi(rawEntry2)
	doorPublic = v2

	return cardPublic, doorPublic
}

func findLoop(start, target int) int {
	i := 1
	n := start
	for {
		n *= SUBJECT
		n %= DIV

		if n == target {
			return i
		}

		i++
	}
}

func process(cardPublic, doorPublic int) int {
	carLoop := findLoop(1, cardPublic)

	resp := 1 
	for i:= 0; i < carLoop; i++ {
		resp *= doorPublic
		resp %= DIV
	}

	return resp
}

func main() {
	k1, k2 := parseInput()
	result := process(k1, k2)

	fmt.Println(result)
}
