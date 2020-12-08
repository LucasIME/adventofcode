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

func parseInput() []bagEntry {
	entries := []bagEntry{}
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
		bagEntry := bagEntry{parentBagName, childs}

		entries = append(entries, bagEntry)
	}

	return entries
}

func invertBagDep(bags []bagEntry) map[string][]string {
	m := make(map[string][]string)
	for _, bagE := range bags {
		for _, c := range bagE.childBags {
			m[c.bagName] = append(m[c.bagName], bagE.parentBag)
		}
	}

	return m
}

func process(bags []bagEntry) int {
	m := invertBagDep(bags)
	visited := make(map[string]bool)
	queue := []string{"shiny gold bags"}
	for len(queue) > 0 {
		cur := queue[0]
		queue = queue[1:]
		if _, ok := visited[cur]; ok {
			continue
		}
		visited[cur] = true
		for _, child := range m[cur] {
			queue = append(queue, child)
		}
	}
	return len(visited) - 1
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
