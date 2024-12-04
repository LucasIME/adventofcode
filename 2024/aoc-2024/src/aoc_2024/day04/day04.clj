(ns aoc-2024.day04.day04
  (:require [aoc-2024.utils.utils :as utils]
             [clojure.string :as str]))

(defn parse-input [lines]
  (utils/to-char-matrix lines)) 


(defn solve [matrix]
  (doseq [i (range (count matrix))
          j (range (count (matrix i)))]
    (println (str "matrix[" i "][" j "] = " (get-in matrix [i j]))))
  )

(defn part1 
  ([] (part1 "day04/input.txt"))
  ([fileName]
  (-> fileName
      (utils/read-file-lines)
      (parse-input)
      (solve))))

(defn part2 
  ([] (part2 "day04/input.txt"))
  ([fileName]
   (-> fileName
   (utils/read-file)
  ;;  (parse-with-dos)
   (solve))))