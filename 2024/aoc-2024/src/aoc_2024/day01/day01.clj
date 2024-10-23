(ns aoc-2024.day01.day01
  (:require [aoc-2024.utils.utils :as utils]))

(defn part1 []
  (let [file "day01/part1.txt"
        lines (utils/read-file file)]
        lines
    ))

(defn part2 []
  (let [file "day01/part2.txt"
        lines (utils/read-file file)
        intLines (map #(Integer/parseInt %) lines)]
        (reduce + intLines)
    ))

;; (doseq [line (part1)] 
;;   (println line))
