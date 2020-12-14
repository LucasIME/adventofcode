package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

const (
	MASK = "mask"
	MEM  = "mem"
)

type command struct {
	commandType string
	target      int
	value       string
}

func parseInput() []command {
	entries := []command{}
	scanner := bufio.NewScanner(os.Stdin)

	for scanner.Scan() {
		rawEntry := scanner.Text()
		splits := strings.Split(rawEntry, " = ")

		var valueToAdd command

		if splits[0] == MASK {
			valueToAdd = command{MASK, 0, splits[1]}
		} else {
			rawMemAddress := splits[0][4 : len(splits[0])-1]
			memAddress, _ := strconv.Atoi(rawMemAddress)
			valueToAdd = command{MEM, memAddress, splits[1]}
		}

		entries = append(entries, valueToAdd)
	}

	return entries
}

func maskToBitMap(mask string) map[int]int {
	result := make(map[int]int)

	for i, v := range mask {
		if v == 'X' {
			continue
		}
		intV, _ := strconv.Atoi(string(v))
		result[35-i] = intV
	}
	return result
}

func memUpdateHelper(memory, mask map[int]int, address, value, bitPos int, bitSoFar []int) map[int]int {
	if bitPos == 36 { // check if this should be 36 or 35. i think 36
		targetAddress := address
		for i, v := range bitSoFar {
			if v == 1 {
				targetAddress |= 1 << i
			} else if v == -1 {
				targetAddress &= ^(1 << i)
			}
		}

		memory[targetAddress] = value
		return memory
	}

	if v, ok := mask[bitPos]; ok {
		return memUpdateHelper(memory, mask, address, value, bitPos+1, append(bitSoFar, v))
	}

	memory = memUpdateHelper(memory, mask, address, value, bitPos+1, append(bitSoFar, 1))
	// -1 is an encoding for saying: this is a zero forced by the X on the Mask. Which is different from the zero that just keeps the old value
	memory = memUpdateHelper(memory, mask, address, value, bitPos+1, append(bitSoFar, -1))
	return memory
}

func realUpdateMemory(memory map[int]int, mask map[int]int, address int, value int) map[int]int {
	for i, v := range mask {
		if v == 1 {
			address |= 1 << i
		}
	}

	return memUpdateHelper(memory, mask, address, value, 0, []int{})

}

func updateMemory(memory map[int]int, cmd command, mask map[int]int) map[int]int {
	numberToAdd, _ := strconv.Atoi(cmd.value)

	return realUpdateMemory(memory, mask, cmd.target, numberToAdd)
}

func memSum(memory map[int]int) int {
	result := 0
	for _, v := range memory {
		result += v
	}

	return result
}

func process(commands []command) int {
	memory := make(map[int]int)
	var mask map[int]int
	for _, command := range commands {
		if command.commandType == MASK {
			mask = maskToBitMap(command.value)
		} else {
			memory = updateMemory(memory, command, mask)
		}
	}

	return memSum(memory)
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
