(ns aoc-2024.day06.day06
  (:require [aoc-2024.utils.utils :as utils]))

(def directions [[-1 0] [0 1] [1 0] [0 -1]])

(defn find-start [matrix target]
 (first (for [row (range (count matrix)) 
              col (range (count (get matrix row))) 
              :when (= target (get-in matrix [row col]))] 
          [row col])))

(defn parse-input [lines]
  (let [matrix (utils/to-char-matrix lines)
        start-pos (find-start matrix "^")
        clean-matrix (assoc-in matrix start-pos ".")]
    [clean-matrix start-pos 0])) 

(defn is-out-of-bounds? [grid [row col]]
  (nil? (get-in grid [row col])))

(defn next-pos-in-dir [[row col] dir-pos]
  (let [[drow dcol] (get directions dir-pos)]
    [(+ row drow) (+ col dcol)]))

(defn next-dir [dir-pos]
  (rem (inc dir-pos) (count directions)))

(defn next-pos [grid pos dir-pos]
  (let [maybe-next-pos (next-pos-in-dir pos dir-pos)] 
    (if (= (get-in grid maybe-next-pos) "#")
      (next-pos grid pos (next-dir dir-pos))
      [maybe-next-pos dir-pos])))

(defn solve [[matrix start-pos dir]]
  (loop [grid matrix pos start-pos dir-pos dir visited #{}]
    (cond
      (is-out-of-bounds? grid pos) (count visited)
      :else (let [[new-pos new-dir] (next-pos grid pos dir-pos)
                  extended-visited (conj visited pos)]
              (recur grid new-pos new-dir extended-visited)))))

(defn part1 
  ([] (part1 "day06/input.txt"))
  ([fileName]
  (-> fileName
      (utils/read-file-lines)
      (parse-input)
      (solve))))
