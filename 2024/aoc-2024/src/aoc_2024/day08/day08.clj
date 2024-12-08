(ns aoc-2024.day08.day08
  (:require [aoc-2024.utils.utils :as utils]))

(defn get-signals [matrix]
  (for [row (range (count matrix))
        col (range (count (get matrix row)))
        :let [value (get-in matrix [row col])]
        :when (not= value ".")]
    {:value value :coords [row col]}))

(defn parse-input [lines]
  (let [matrix (utils/to-char-matrix lines)
        val-to-coords (->> matrix 
                           (get-signals) 
                           (group-by :value) 
                           (map (fn [[k v]] [k (mapv :coords v)])) 
                           (into {}))]
    [val-to-coords matrix])) 

(defn every-pair [array]
  (for [i (range (count array))
        j (range (inc i) (count array))]
    [(nth array i) (nth array j)]))

(defn get-antinodes [[[row1 col1] [row2 col2]]]
  (let [[vec_row vec_col] [(- row2 row1) (- col2 col1)]
        p1 [(+ row2 vec_row) (+ col2 vec_col)]
        p2 [(- row1 vec_row) (- col1 vec_col)]]
    [p1 p2]))

(defn get-antinodes-for-target [[target coordinates]]
  (let [paired-coords (every-pair coordinates)
        all-anti (apply concat (map get-antinodes paired-coords))]
    all-anti))

(defn is-inside? [[row col] matrix]
  (and
   (>= row 0)
   (>= col 0)
   (< row (count matrix))
   (< col (count (get matrix row)))))


(defn solve [[signal-to-locations matrix]]
  (let [signal-to-antinodes (map get-antinodes-for-target signal-to-locations)
        all-antinodes (set (apply concat signal-to-antinodes))
        bounded-antinodes (filter #(is-inside? %1 matrix) all-antinodes)]
    (count bounded-antinodes)))

(defn part1 
  ([] (part1 "day08/input.txt"))
  ([file-name]
  (-> file-name
      (utils/read-file-lines)
      (parse-input)
      (solve))))
