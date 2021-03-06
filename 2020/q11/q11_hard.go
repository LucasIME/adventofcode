package main

import (
	"bufio"
	"fmt"
	"os"
)

const (
	EMPTY    = 'L'
	OCCUPIED = '#'
	FLOOR    = '.'
)

func parseInput() [][]rune {
	entries := [][]rune{}
	scanner := bufio.NewScanner(os.Stdin)

	for scanner.Scan() {
		rawEntry := scanner.Text()
		entries = append(entries, []rune(rawEntry))
	}

	return entries
}

func copySeatMatrix(seats [][]rune) [][]rune {
	resp := [][]rune{}
	for i, row := range seats {
		resp = append(resp, []rune{})
		for _, c := range row {
			resp[i] = append(resp[i], c)
		}
	}
	return resp
}

func isValidSeat(seats [][]rune, i, j int) bool {
	return i >= 0 && j >= 0 && i < len(seats) && j < len(seats[i])
}

func getNeighs(seats [][]rune, i, j int) []rune {
	resp := []rune{}
	//upper
	for nI := i - 1; nI >= 0; nI-- {
		if seats[nI][j] == OCCUPIED {
			resp = append(resp, OCCUPIED)
			break
		}
		if seats[nI][j] == EMPTY {
			break
		}
	}

	// lower
	for nI := i + 1; nI < len(seats); nI++ {
		if seats[nI][j] == OCCUPIED {
			resp = append(resp, OCCUPIED)
			break
		}
		if seats[nI][j] == EMPTY {
			break
		}
	}

	// left
	for nC := j - 1; nC >= 0; nC-- {
		if seats[i][nC] == OCCUPIED {
			resp = append(resp, OCCUPIED)
			break
		}
		if seats[i][nC] == EMPTY {
			break
		}
	}

	// right
	for nC := j + 1; nC < len(seats[i]); nC++ {
		if seats[i][nC] == OCCUPIED {
			resp = append(resp, OCCUPIED)
			break
		}
		if seats[i][nC] == EMPTY {
			break
		}
	}

	// lower right
	for nI, nJ := i+1, j+1; nI < len(seats) && nJ < len(seats[i]); nI, nJ = nI+1, nJ+1 {
		if seats[nI][nJ] == OCCUPIED {
			resp = append(resp, OCCUPIED)
			break
		}
		if seats[nI][nJ] == EMPTY {
			break
		}
	}

	// upper left
	for nI, nJ := i-1, j-1; nI >= 0 && nJ >= 0; nI, nJ = nI-1, nJ-1 {
		if seats[nI][nJ] == OCCUPIED {
			resp = append(resp, OCCUPIED)
			break
		}
		if seats[nI][nJ] == EMPTY {
			break
		}
	}

	// upper right
	for nI, nJ := i-1, j+1; nI >= 0 && nJ < len(seats[i]); nI, nJ = nI-1, nJ+1 {
		if seats[nI][nJ] == OCCUPIED {
			resp = append(resp, OCCUPIED)
			break
		}
		if seats[nI][nJ] == EMPTY {
			break
		}
	}

	// lower left
	for nI, nJ := i+1, j-1; nI < len(seats) && nJ >= 0; nI, nJ = nI+1, nJ-1 {
		if seats[nI][nJ] == OCCUPIED {
			resp = append(resp, OCCUPIED)
			break
		}
		if seats[nI][nJ] == EMPTY {
			break
		}
	}

	return resp
}

func getNextSeatValue(seats [][]rune, i int, j int) rune {
	if seats[i][j] == FLOOR {
		return FLOOR
	}
	curSeat := seats[i][j]
	neighs := getNeighs(seats, i, j)
	emptyCount := 0
	occupiedCount := 0
	for _, value := range neighs {
		if value == OCCUPIED {
			occupiedCount++
		} else if value == EMPTY {
			emptyCount++
		}
	}

	if curSeat == EMPTY && occupiedCount == 0 {
		return OCCUPIED
	}

	if curSeat == OCCUPIED && occupiedCount >= 5 {
		return EMPTY
	}

	return curSeat
}

func nextSeatArrangement(seats [][]rune) [][]rune {
	nextSeats := copySeatMatrix(seats)
	for i, row := range seats {
		for j, _ := range row {
			nextSeats[i][j] = getNextSeatValue(seats, i, j)
		}
	}

	return nextSeats
}

func countOccupied(seats [][]rune) int {
	total := 0
	for _, row := range seats {
		for _, seat := range row {
			if seat == OCCUPIED {
				total++
			}
		}
	}

	return total
}

func isEqual(seats1, seats2 [][]rune) bool {
	for i, r := range seats1 {
		for j, c1 := range r {
			if c1 != seats2[i][j] {
				return false
			}
		}
	}

	return true
}

func process(seats [][]rune) int {
	lastSeats := seats
	nextSeats := nextSeatArrangement(lastSeats)
	for !isEqual(nextSeats, lastSeats) {
		lastSeats = nextSeats
		nextSeats = nextSeatArrangement(lastSeats)
	}

	return countOccupied(lastSeats)
}

func printSeats(seats [][]rune) {
	for _, row := range seats {
		fmt.Println(string(row))
	}
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
