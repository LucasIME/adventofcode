package main

import (
	"bufio"
	"fmt"
	"os"
)

func getMax(a, b int) int {
	if a >= b {
		return a
	}

	return b
}

func getSeat(seat string) []int {
	rowRegion := seat[:7]
	colRegion := seat[7:]

	lowRow := 0
	highRow := 127
	for _, v := range rowRegion {
		switch v {
		case 'B':
			lowRow = ((highRow + lowRow) / 2) + 1
		case 'F':
			highRow = (highRow + lowRow) / 2
		}
	}

	lowCol := 0
	highCol := 7

	for _, v := range colRegion {
		switch v {
		case 'R':
			lowCol = ((highCol + lowCol) / 2) + 1
		case 'L':
			highCol = (highCol + lowCol) / 2
		}
	}

	return []int{lowRow, lowCol}
}

func getSeatId(seat []int) int {
	return seat[0]*8 + seat[1]
}

func getRawSeatId(rawSeat string) int {
	seat := getSeat(rawSeat)
	return getSeatId(seat)
}

func parseInput() []string {
	entries := []string{}
	scanner := bufio.NewScanner(os.Stdin)

	for scanner.Scan() {
		rawEntry := scanner.Text()
		entries = append(entries, rawEntry)
	}

	return entries
}

func getAllPossibleSeats() [][]int {
	result := [][]int{}
	for row := 0; row <= 127; row++ {
		for col := 0; col <= 7; col++ {
			result = append(result, []int{row, col})
		}
	}

	return result
}

func getSeatMap(seats [][]int) map[int][]int {
	result := make(map[int][]int)
	for _, seat := range seats {
		result[getSeatId(seat)] = seat
	}

	return result
}

func removeLowSeats(seatMap map[int][]int) map[int][]int {
	for id, seat := range seatMap {
		if seat[0] <= 5 {
			delete(seatMap, id)
		}
	}

	return seatMap
}

func removeHighSeats(seatMap map[int][]int) map[int][]int {
	for id, seat := range seatMap {
		if seat[0] >= 115 {
			delete(seatMap, id)
		}
	}

	return seatMap
}

func process(rawSeats []string) int {
	allSeats := getAllPossibleSeats()
	seatMap := getSeatMap(allSeats)

	for _, rawSeat := range rawSeats {
		id := getRawSeatId(rawSeat)
		delete(seatMap, id)
	}

	seatMap = removeLowSeats(seatMap)
	seatMap = removeHighSeats(seatMap)

	//There should be only one seat now
	for id := range seatMap {
		return id
	}

	panic("No seat found!")
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
