package main

import (
	"bufio"
	"fmt"
	"os"
	"strings"
)

type recipe struct {
	ingredients []string
	alergens    []string
}

func parseInput() []recipe {
	scanner := bufio.NewScanner(os.Stdin)
	entries := []recipe{}

	for scanner.Scan() {
		rawEntry := scanner.Text()
		splits := strings.Split(rawEntry, " (contains ")
		rawIngredients := splits[0]
		ingredients := strings.Split(rawIngredients, " ")

		rawAllergenList := splits[1][0 : len(splits[1])-1] // ignoring tailing ")"
		allergens := strings.Split(rawAllergenList, ", ")

		entries = append(entries, recipe{ingredients, allergens})
	}

	return entries
}

func setIntersection(s1, s2 map[string]bool) map[string]bool {
	out := make(map[string]bool)
	for k, _ := range s1 {
		if _, ok := s2[k]; ok {
			out[k] = true
		}
	}

	for k, _ := range s2 {
		if _, ok := s1[k]; ok {
			out[k] = true
		}
	}

	return out
}

func toMap(s []string) map[string]bool {
	resp := make(map[string]bool)
	for _, v := range s {
		resp[v] = true
	}

	return resp
}

func getPossibleAlergens(recipes []recipe) map[string]map[string]bool {
	alergenToPossible := make(map[string]map[string]bool)

	for _, r := range recipes {
		for _, allergen := range r.alergens {

			if curCandidates, ok := alergenToPossible[allergen]; ok {
				alergenToPossible[allergen] = setIntersection(curCandidates, toMap(r.ingredients))
			} else {
				alergenToPossible[allergen] = toMap(r.ingredients)
			}
		}
	}

	return alergenToPossible
}

func getAllIngredients(recipes []recipe) map[string]bool {
	allIng := make(map[string]bool)

	for _, recipe := range recipes {
		for _, ing := range recipe.ingredients {
			allIng[ing] = true
		}
	}

	return allIng
}

func getIngredientsNotInAnyAlergens(recipes []recipe) map[string]bool {
	resp := make(map[string]bool)

	alergensToPossible := getPossibleAlergens(recipes)
	allIngredients := getAllIngredients(recipes)

	for ing, _ := range allIngredients {
		isSafe := true
		for _, allergicIngs := range alergensToPossible {
			if _, ok := allergicIngs[ing]; ok {
				isSafe = false
				break
			}
		}

		if isSafe {
			resp[ing] = true
		}
	}

	return resp
}

func process(recipes []recipe) int {
	total := 0

	nonAlergenIngredients := getIngredientsNotInAnyAlergens(recipes)

	for _, recipe := range recipes {
		for _, ing := range recipe.ingredients {
			if _, ok := nonAlergenIngredients[ing]; ok {
				total++
			}
		}
	}

	return total
}

func main() {
	entries := parseInput()
	result := process(entries)

	fmt.Println(result)
}
