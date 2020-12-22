package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
)

type game struct {
	oneDeck []int
	twoDeck []int
}

func (g *game) isDone() bool {
	return len(g.oneDeck) == 0 || len(g.twoDeck) == 0
}

func (g *game) play() {
	p1Card := g.oneDeck[0]
	p2Card := g.twoDeck[0]

	if p2Card > p1Card {
		g.oneDeck = g.oneDeck[1:]
		g.twoDeck = append(g.twoDeck[1:], p2Card, p1Card)
	} else {
		g.oneDeck = append(g.oneDeck[1:], p1Card, p2Card)
		g.twoDeck = g.twoDeck[1:]
	}
}

func (g *game) winnerScore() int {
	total := 0
	if len(g.oneDeck) != 0 {
		for i, v := range g.oneDeck {
			total += (len(g.oneDeck) - i) * v
		}

		return total
	}

	for i, v := range g.twoDeck {
		total += (len(g.twoDeck) - i) * v
	}

	return total

}

func parseInput() game {
	scanner := bufio.NewScanner(os.Stdin)
	player := 0

	oneDeck := []int{}
	twoDeck := []int{}

	for scanner.Scan() {
		rawEntry := scanner.Text()

		if rawEntry == "" {
			continue
		}

		if rawEntry[0] == 'P' {
			player++
			continue
		}

		intV, _ := strconv.Atoi(rawEntry)
		if player == 1 {
			oneDeck = append(oneDeck, intV)
		} else {
			twoDeck = append(twoDeck, intV)
		}
	}

	return game{oneDeck, twoDeck}
}

func process(g game) int {
	for !g.isDone() {
		g.play()
	}

	return g.winnerScore()
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
