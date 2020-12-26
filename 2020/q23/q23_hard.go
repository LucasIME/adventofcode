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

func toOneMillion(head *node) {
	cur := head
	for i := 0; i < 8; i++ {
		cur = cur.next
	}

	for i := 10; i <= 1_000_000; i++ {
		cur.next = &node{i, nil}
		cur = cur.next
		if i == 1_000_000 {
			cur.next = head
		}
	}
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
		//candidate = 9
		candidate = 1_000_000
	}

	for contains(valuesRemoved, candidate) {
		candidate--
		if candidate == 0 {
			//candidate = 9
			candidate = 1_000_000
		}
	}

	return candidate
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

func makeValToNodeMap(head *node) map[int]*node {
	out := make(map[int]*node)
	cur := head
	for i := 0; i <= 1_000_000; i++ {
		out[cur.val] = cur
		cur = cur.next
	}

	return out
}

func process(cups *node) int {
	var curCup *node
	curCup = cups
	valToNode := makeValToNodeMap(cups)

	for move := 0; move < 10_000_000; move++ {
		curVal := curCup.val

		firstRemoved, lastRemoved, valuesRemoved := removeNext3(curCup)

		destinationCupValue := findDestinationValue(curVal, valuesRemoved)

		destCup := valToNode[destinationCupValue]
		insert(destCup, firstRemoved, lastRemoved)

		curCup = curCup.next
	}

	oneCup := valToNode[1]
	c1 := oneCup.next
	c2 := oneCup.next.next

	return c1.val * c2.val
}

func main() {
	entries := parseInput()
	head := toLinkedList(entries)
	toOneMillion(head)
	result := process(head)

	fmt.Println(result)
}
