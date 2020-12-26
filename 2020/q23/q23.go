package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

type node struct {
	val  int
	next *node
}

func toLinkedList(v []int) *node {
	head := node{v[0], nil}
	var last *node
	last = &head

	for i := 1; i < len(v); i++ {
		val := v[i]
		newNode := node{val, nil}
		last.next = &newNode
		last = &newNode
		if i == len(v)-1 {
			last.next = &head
		}
	}

	return &head
}

func parseInput() []int {
	scanner := bufio.NewScanner(os.Stdin)
	out := []int{}

	for scanner.Scan() {
		rawEntry := scanner.Text()

		for _, c := range rawEntry {
			v, _ := strconv.Atoi(string(c))
			out = append(out, v)
		}
	}

	return out
}

func contains(v []int, val int) bool {
	for _, x := range v {
		if x == val {
			return true
		}
	}

	return false
}

func findDestinationValue(curVal int, valuesRemoved []int) int {
	candidate := curVal - 1

	if candidate == 0 {
		candidate = 9
	}

	for contains(valuesRemoved, candidate) {
		candidate--
		if candidate == 0 {
			candidate = 9
		}
	}

	return candidate
}

func getDestCup(cups *node, cupVal int) *node {
	cur := cups
	for {
		if cupVal == cur.val {
			return cur
		}

		cur = cur.next
	}
}

func insert(baseCup, firstCup, thirdCup *node) {
	var originalFollow *node
	originalFollow = baseCup.next

	thirdCup.next = originalFollow
	baseCup.next = firstCup
}

func removeNext3(cups *node) (*node, *node, []int) {
	head := cups

	firstToBeRemoved := head.next
	lastToBeRemoved := firstToBeRemoved.next.next

	head.next = &(*lastToBeRemoved.next)
	lastToBeRemoved.next = nil

	return firstToBeRemoved, lastToBeRemoved, []int{firstToBeRemoved.val, firstToBeRemoved.next.val, lastToBeRemoved.val}
}

func process(cups *node) string {
	var curCup *node
	curCup = cups

	for move := 0; move < 100; move++ {
		curVal := curCup.val

		firstRemoved, lastRemoved, valuesRemoved := removeNext3(curCup)

		destinationCupValue := findDestinationValue(curVal, valuesRemoved)

		destCup := getDestCup(curCup, destinationCupValue)
		insert(destCup, firstRemoved, lastRemoved)

		curCup = curCup.next
	}

	oneCup := getDestCup(curCup, 1)
	out := ""
	for i := 0; i < 8; i++ {
		out += fmt.Sprint(oneCup.next.val)
		oneCup = oneCup.next
	}

	return out
}

func main() {
	entries := parseInput()
	head := toLinkedList(entries)
	result := process(head)

	fmt.Println(result)
}
