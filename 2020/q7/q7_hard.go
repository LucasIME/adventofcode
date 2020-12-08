package main

import (
	"bufio"
	"fmt"
	"os"
	"strconv"
	"strings"
)

type bagEntry struct {
	parentBag string
	childBags []childBag
}

type childBag struct {
	bagName string
	qty     int
}

func parseInput() map[string]bagEntry {
	entries := make(map[string]bagEntry)
	scanner := bufio.NewScanner(os.Stdin)

	for scanner.Scan() {
		rawEntry := scanner.Text()
		rawEntry = rawEntry[:len(rawEntry)-1] // remove trailing dot
		containers := strings.Split(rawEntry, " contain ")
		parentBagName := containers[0]
		rawChilds := strings.Split(containers[1], ", ")
		childs := []childBag{}
		for _, c := range rawChilds {
			if c == "no other bags" {
				continue
			}
			c_num, _ := strconv.Atoi(string(c[0]))
			c_name := c[2:]
			if c_name[len(c_name)-1] == 'g' {
				c_name = c_name + "s"
			}

			childs = append(childs, childBag{c_name, c_num})
		}
		entries[parentBagName] = bagEntry{parentBagName, childs}
	}

	return entries
}

func getTotalBags(bagName string, bags map[string]bagEntry) int {
	total := 1
	for _, child := range bags[bagName].childBags {
		total += child.qty * getTotalBags(child.bagName, bags)
	}

	return total
}

func process(bags map[string]bagEntry) int {
	return getTotalBags("shiny gold bags", bags) - 1
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
