(ns aoc-2024.day10.day10
  (:require [aoc-2024.utils.utils :as utils]))


(defn parse-input [lines]
  (utils/to-char-matrix lines))

(defn get-neighs [grid [row col]] 
  (let [directions [[0 1] [0 -1] [1 0] [-1 0]]]
    (filter #(not= %1 nil) (map (fn [[rv rc]] [(+ row rv) (+ col rc)]) directions))))

(defn get-trail-score 
  [grid [row col]]
  (let [cur (Integer/parseInt (get-in grid [row col]))
        neighs (map #(Integer/parseInt %1) (get-neighs grid [row col]))
        ]))

(defn map-trails [grid]
  (for [row (range (count grid))
        col (range (count (get grid row))) 
        :when (= "0" (get-in grid [row col]))]
    (get-trail-score grid [row col])))

(defn solve [grid]
  (reduce + (map-trails grid)))

(defn part1 
  ([] (part1 "day10/input.txt"))
  ([file-name]
  (-> file-name
      (utils/read-file-lines)
      (parse-input)
      (solve))))

(part1 "day10/ex5.txt")