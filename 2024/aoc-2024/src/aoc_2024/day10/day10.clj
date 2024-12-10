(ns aoc-2024.day10.day10
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.set :as set]))

(defn parse-input [lines]
  (utils/to-char-matrix lines))

(defn get-neighs [grid [row col]] 
  (let [directions [[0 1] [0 -1] [1 0] [-1 0]]
        cur-val (Integer/parseInt (get-in grid [row col]))]
    (filter (fn [[r c]] (and
                         (not= (get-in grid [r c]) nil)
                         (= (inc cur-val) (Integer/parseInt (get-in grid [r c])))
                         ) )
            (map (fn [[rv rc]] [(+ row rv) (+ col rc)]) directions))))

(defn count-trail-peaks 
  [grid [row col] out]
  (let [cur (Integer/parseInt (get-in grid [row col]))
        valid-neighs (get-neighs grid [row col])] 
    (if (= 9 cur) 
      (conj out [row col]) 
      (reduce set/union (map (fn [[r c]] (count-trail-peaks grid [r c] out)) valid-neighs)))))

(defn map-trails [grid]
  (for [row (range (count grid))
        col (range (count (get grid row))) 
        :when (= "0" (get-in grid [row col]))]
    (count  (count-trail-peaks grid [row col] #{}))))

(defn solve [grid]
  (let [trails (map-trails grid)]
    (reduce + trails)))

(defn part1 
  ([] (part1 "day10/input.txt"))
  ([file-name]
  (-> file-name
      (utils/read-file-lines)
      (parse-input)
      (solve))))

(defn count-trail-path 
  [grid [row col]]
  (let [
        cur (Integer/parseInt (get-in grid [row col]))
        valid-neighs (get-neighs grid [row col])
        ]
  (if (= 9 cur)
    1
    (reduce + (map (fn [[r c]] (count-trail-path grid [r c])) valid-neighs)))))

(defn map-trails2 [grid]
  (for [row (range (count grid))
        col (range (count (get grid row))) 
        :when (= "0" (get-in grid [row col]))]
      (count-trail-path grid [row col])))

(defn solve2 [grid]
  (let [trails (map-trails2 grid)]
    (reduce + trails)))


(defn part2 
  ([] (part2 "day10/input.txt"))
  ([file-name]
  (-> file-name
      (utils/read-file-lines)
      (parse-input)
      (solve2))))

(part2 "day10/ex5.txt")
