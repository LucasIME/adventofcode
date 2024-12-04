(ns aoc-2024.day04.day04
  (:require [aoc-2024.utils.utils :as utils]
             [clojure.string :as str]))

(defn parse-input [lines]
  (utils/to-char-matrix lines)) 

(def direction-vectors '((1 0) (-1 0) (0 1) (0 -1) (1 1) (1 -1) (-1 1) (-1 -1)))

(map #(+ (first %1) (second %1)) direction-vectors)

(defn get-word-in-direction [matrix row col direction]
  (loop [m matrix i row j col out '()]
    (let [maybe_item (get-in m [i j])]
    (case maybe_item
      nil  (str/join "" (reverse out))
      (recur m (+ i (first direction)) (+ j (second direction)) (cons maybe_item out)))
    )
  ))

(defn get-cross-neighs [matrix row col]
  (map #(get-word-in-direction matrix row col %1) direction-vectors))


(defn solve [matrix]
  (let [nested_neighs (for [i (range (count matrix)) 
                            j (range (count (matrix i)))] 
                        (get-cross-neighs matrix i j))]
    (->> nested_neighs
         (apply concat)
         (filter #(str/starts-with? %1 "XMAS"))
         (count))))

(defn part1 
  ([] (part1 "day04/input.txt"))
  ([fileName]
  (-> fileName
      (utils/read-file-lines)
      (parse-input)
      (solve))))

(defn is-xmas? [matrix row col]
  (let [cur (get-in matrix [row col])
        top-left (get-in matrix [(dec row) (dec col)])
        bot-left (get-in matrix [(inc row) (dec col)])
        top-right (get-in matrix [(dec row) (inc col)])
        bot-right (get-in matrix [(inc row) (inc col)])]
        (and
         (= cur "A")
         (or (and (= top-left "S") (= bot-right "M")) (and (= top-left "M") (= bot-right "S")))
         (or (and (= top-right "S") (= bot-left "M")) (and (= top-right "M") (= bot-left "S")))
         )
    ))

(defn solve2 [matrix]
  (let [maybe_xmas (for [i (range (count matrix)) 
                            j (range (count (matrix i)))] 
                        (is-xmas? matrix i j))]
    (->> maybe_xmas
         (filter #(= true %1))
         (count))))

(defn part2 
  ([] (part2 "day04/input.txt"))
  ([fileName]
  (-> fileName
      (utils/read-file-lines)
      (parse-input)
      (solve2))))