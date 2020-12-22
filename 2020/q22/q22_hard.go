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

	visitedStates map[string]bool
	hasEnded      bool
	winner        int
}

func arrayCopy(v []int) []int {
	c := make([]int, len(v))
	for i, x := range v {
		c[i] = x
	}

	return c
}

func (g *game) isDone() bool {
	return g.hasEnded || len(g.oneDeck) == 0 || len(g.twoDeck) == 0
}

func (g *game) play() {
	stateHash := g.toHashableState()
	if _, ok := g.visitedStates[stateHash]; ok {
		g.hasEnded = true
		g.winner = 1
		return
	}

	g.visitedStates[stateHash] = true

	p1Card := g.oneDeck[0]
	p2Card := g.twoDeck[0]
	g.oneDeck = g.oneDeck[1:]
	g.twoDeck = g.twoDeck[1:]

	if len(g.oneDeck) >= p1Card && len(g.twoDeck) >= p2Card {
		newGame := game{arrayCopy(g.oneDeck)[:p1Card], arrayCopy(g.twoDeck)[:p2Card], make(map[string]bool), false, -1}
		newGame.playUntilWinner()

		if newGame.winner == 1 {
			g.oneDeck = append(g.oneDeck, p1Card, p2Card)
		} else {
			g.twoDeck = append(g.twoDeck, p2Card, p1Card)
		}

		return
	}

	if p2Card > p1Card {
		g.twoDeck = append(g.twoDeck, p2Card, p1Card)
	} else {
		g.oneDeck = append(g.oneDeck, p1Card, p2Card)
	}

	g.maybeUpdateWinner()
}

func (g *game) maybeUpdateWinner() {
	if len(g.oneDeck) == 0 {
		g.hasEnded = true
		g.winner = 2
		return
	}

	if len(g.twoDeck) == 0 {
		g.hasEnded = true
		g.winner = 1
		return
	}
}

func (g *game) playUntilWinner() {
	for !g.isDone() {
		g.play()
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

func (g *game) toHashableState() string {
	p1 := fmt.Sprint(g.oneDeck)
	p2 := fmt.Sprint(g.twoDeck)

	return p1 + "," + p2
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

	return game{oneDeck, twoDeck, make(map[string]bool), false, -1}
}

func process(g game) int {
	g.playUntilWinner()
	return g.winnerScore()
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
