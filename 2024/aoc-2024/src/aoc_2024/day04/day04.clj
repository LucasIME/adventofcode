(ns aoc-2024.day04.day04
  (:require [aoc-2024.utils.utils :as utils]
             [clojure.string :as str]))

(defn parse-input [lines]
 lines)

(defn solve [pairs]
  (->> pairs 
       (map #(* (first %1) (second %1))) 
       (reduce +)))

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