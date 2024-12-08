(ns aoc-2024.day08.day08
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.string :as str]))

(defn parse-line [line]
  (let [[raw_target raw_nums_str] (str/split line #": ")
        raw_nums (str/split raw_nums_str #" ")]
    [(Long/parseLong raw_target)
     (map #(Long/parseLong %1) raw_nums)]))

(defn parse-input [lines]
  (map parse-line lines)) 

(defn solve [equations ops]
  (->> equations
       (filter #(is-solvable? ops %1) )
       (map first)
       (reduce +)))

(defn part1 
  ([] (part1 "day08/input.txt"))
  ([fileName]
  (-> fileName
      (utils/read-file-lines)
      (parse-input)
      (solve (list + *)))))