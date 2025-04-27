package main

import (
	"fmt"
	"io"
	"maps"
	"math"
	"os"
	"strings"
)

type TileKey string

type Tile struct {
	number int
	grid   [][]rune
}

func (t Tile) Key() TileKey {
	var sb strings.Builder
	sb.WriteString(fmt.Sprintf("%d-", t.number))
	for _, row := range t.grid {
		sb.WriteString(string(row))
		sb.WriteString("\n")
	}
	return TileKey(sb.String())
}

func parseTile(lines []string) Tile {
	number := 0
	fmt.Sscanf(lines[0], "Tile %d:", &number)

	grid := make([][]rune, len(lines)-1)
	for i, line := range lines[1:] {
		grid[i] = []rune(line)
	}

	return Tile{number: number, grid: grid}
}

func (t *Tile) flipRegardingHoziontalLine() Tile {
	size := len(t.grid[0])
	newGrid := make([][]rune, size)
	for i := 0; i < size; i++ {
		newGrid[i] = make([]rune, size)
		copy(newGrid[i], t.grid[size-1-i])
	}
	return Tile{number: t.number, grid: newGrid}
}

func (t *Tile) flipRegardingVerticalLine() Tile {
	colSize := len(t.grid[0])
	newGrid := make([][]rune, len(t.grid))
	for row := 0; row < len(t.grid); row++ {
		newLine := make([]rune, colSize)
		for col := 0; col < colSize; col++ {
			newLine[col] = t.grid[row][colSize-1-col]
		}
		newGrid[row] = newLine
	}
	return Tile{number: t.number, grid: newGrid}

}

func (t *Tile) rotateClockwise() Tile {
	newGrid := make([][]rune, len(t.grid))
	colSize := len(t.grid[0])
	for row := 0; row < len(t.grid); row++ {
		newLine := make([]rune, len(t.grid))
		for col := 0; col < colSize; col++ {
			newLine[col] = t.grid[colSize-1-col][row]
		}
		newGrid[row] = newLine
	}

	return Tile{number: t.number, grid: newGrid}
}

func (t *Tile) allRotations() []Tile {
	rotations := make([]Tile, 4)
	rotations[0] = *t
	for i := 1; i < 4; i++ {
		rotations[i] = rotations[i-1].rotateClockwise()
	}
	return rotations
}

func (t *Tile) allPossibilities() []Tile {
	allRotations := t.allRotations()
	allPossibilities := allRotations

	for _, tile := range allRotations {
		allPossibilities = append(allPossibilities, tile.flipRegardingHoziontalLine())
		allPossibilities = append(allPossibilities, tile.flipRegardingVerticalLine())
	}

	return allPossibilities
}

func (t *Tile) topEdge() []rune {
	return t.grid[0]
}

func (t *Tile) bottomEdge() []rune {
	return t.grid[len(t.grid)-1]
}

func (t *Tile) leftEdge() []rune {
	edge := make([]rune, len(t.grid))
	for row := 0; row < len(t.grid); row++ {
		edge[row] = t.grid[row][0]
	}
	return edge
}

func (t *Tile) rightEdge() []rune {
	edge := make([]rune, len(t.grid))
	for row := 0; row < len(t.grid); row++ {
		edge[row] = t.grid[row][len(t.grid[row])-1]
	}
	return edge
}

func parseInput() []Tile {
	data, _ := io.ReadAll(os.Stdin)

	blocks := strings.Split(string(data), "\n\n")

	tiles := make([]Tile, 0)
	for _, block := range blocks {
		lines := strings.Split(block, "\n")
		tile := parseTile(lines)
		tiles = append(tiles, tile)
	}

	return tiles
}

func slicesEqual(slice1, slice2 []rune) bool {
	if len(slice1) != len(slice2) {
		return false
	}

	for i := range len(slice1) {
		if slice1[i] != slice2[i] {
			return false
		}
	}

	return true
}

func makeBigTileHelper(pickedTiles []Tile, i int, availableTiles map[TileKey]Tile, size int) ([]Tile, bool) {
	if len(pickedTiles) == size*size && i == size*size {
		return pickedTiles, true
	}

	row := i / size
	col := i % size

	for candidateKey, candidateBaseTile := range availableTiles {
		for _, candidateTile := range candidateBaseTile.allPossibilities() {
			if row != 0 {
				topTile := pickedTiles[(row-1)*size+col]
				if !slicesEqual(topTile.bottomEdge(), candidateTile.topEdge()) {
					continue
				}
			}

			if col != 0 {
				leftTile := pickedTiles[row*size+col-1]
				if !slicesEqual(leftTile.rightEdge(), candidateTile.leftEdge()) {
					continue
				}
			}

			pickedTiles = append(pickedTiles, candidateTile)
			newAvailableTiles := maps.Clone(availableTiles)
			delete(newAvailableTiles, candidateKey)

			result, found := makeBigTileHelper(pickedTiles, i+1, newAvailableTiles, size)
			if found {
				return result, true
			}

			pickedTiles = pickedTiles[:len(pickedTiles)-1]
		}
	}

	return nil, false
}

func makeBigTile(tiles []Tile, size int) [][]Tile {
	availableTiles := make(map[TileKey]Tile)
	for _, tile := range tiles {
		availableTiles[tile.Key()] = tile
	}
	bigTileParts, _ := makeBigTileHelper([]Tile{}, 0, availableTiles, size)

	bigTile := make([][]Tile, size)
	for i := range size {
		bigTile[i] = make([]Tile, size)
	}
	for i, tile := range bigTileParts {
		row := i / size
		col := i % size
		bigTile[row][col] = tile
	}

	return bigTile
}

func process(tiles []Tile) int {
	size := int(math.Sqrt(float64(len(tiles))))
	bigTile := makeBigTile(tiles, size)
	return bigTile[0][0].number * bigTile[0][size-1].number * bigTile[size-1][0].number * bigTile[size-1][size-1].number
}

func main() {
	entries := parseInput()
	fmt.Println("Parsed tiles:", entries)
	result := process(entries)

	fmt.Println(result)
}
