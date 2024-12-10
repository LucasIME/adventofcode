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

(defn get-trail-score 
  [grid [row col] out]
  (let [cur (Integer/parseInt (get-in grid [row col]))
        valid-neighs (get-neighs grid [row col])] 
    (if (= 9 cur) 
      (conj out [row col]) 
      (reduce set/union (map (fn [[r c]] (get-trail-score grid [r c] out)) valid-neighs)))))

(defn map-trails [grid]
  (for [row (range (count grid))
        col (range (count (get grid row))) 
        :when (= "0" (get-in grid [row col]))]
    (count  (get-trail-score grid [row col] #{}))))

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
