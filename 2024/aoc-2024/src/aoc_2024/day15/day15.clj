(ns aoc-2024.day15.day15
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.string :as str]))

(def dir-map {"<" [0 -1] "^" [-1 0] ">" [0 1] "v" [1 0]})

(defn get-start-pos [grid]
  (for [row (range (count grid))
        col (range (count (get grid row)))
        :when (= (get-in grid [row col]) "@")] 
    [row col]))

(defn clean-moves [raw-moves]
  (->> raw-moves
       (seq)
       (map str)
       (filter #(not= %1 "\n"))))

(defn parse-input [raw-input]
  (let [[raw-grid raw-moves] (str/split raw-input #"\n\n")
        grid (utils/to-char-matrix (str/split raw-grid #"\n"))
        [start-pos] (get-start-pos grid)
        clean-grid (assoc-in grid start-pos ".")]
    [clean-grid (clean-moves raw-moves) start-pos]))

(defn sum-gps [grid]
  (reduce + (for [row (range (count grid))
                  col (range (count (get grid row)))
                  :when (= "O" (get-in grid [row col]))]
              (+ (* 100 row) col))))

(defn pos-after-dir [[row col] dir]
  (let [[dr dc] (get dir-map dir)]
    [(+ row dr) (+ col dc)]))

(defn can-go? [grid direction pos]
  (let [next-pos (pos-after-dir pos direction)
        next-val (get-in grid next-pos)]
    (cond 
      (= next-val "#") false
      (= next-val ".") true
      :else (can-go? grid direction next-pos))))

(defn move [grid direction initial-pos]
  (loop [grid grid, dir direction, pos initial-pos, last "."]
    (cond 
      (and (not= pos initial-pos)
      (= (get-in grid pos) ".")) (assoc-in grid pos last)
      :else (let [next-pos (pos-after-dir pos dir)]
              (recur (assoc-in grid pos last) dir next-pos (get-in grid pos))))))

(defn next-grid [grid direction pos]
  (if (can-go? grid direction pos)
    [(move grid direction pos) (pos-after-dir pos direction)]
    [grid pos]))

(defn print-grid [grid]
  (doseq [row grid] (println (str/join row))))

(defn find-final-grid [grid moves start-pos]
  (loop [grid grid, moves moves, pos start-pos]
    (cond
      (empty? moves) grid 
      :else (let [[new-grid new-pos] (next-grid grid (first moves) pos)]
              (recur new-grid (rest moves) new-pos)))))

(defn solve [[grid moves start-pos]]
  (let [final-grid (find-final-grid grid moves start-pos)]
    (sum-gps final-grid)))

(defn part1 
  ([] (part1 "day15/input.txt"))
  ([file-name ]
  (-> file-name
      (utils/read-file)
      (parse-input)
      (solve))))