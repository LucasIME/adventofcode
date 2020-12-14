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

func updateMemory(memory map[int]int, cmd command, mask map[int]int) map[int]int {
	numberToAdd, _ := strconv.Atoi(cmd.value)
	realNumberToAdd := 0
	i := 0

	for numberToAdd > 0 {
		bit := numberToAdd % 2
		realNumberToAdd += bit << i
		i++
		numberToAdd >>= 1
	}

	for i, v := range mask {
		if v == 0 {
			realNumberToAdd &= ^(1 << i)
		} else if v == 1 {
			realNumberToAdd |= 1 << i
		}
	}

	memory[cmd.target] = realNumberToAdd
	return memory
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
