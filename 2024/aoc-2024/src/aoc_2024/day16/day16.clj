(ns aoc-2024.day16.day16
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.string :as str]))

(defn parse-input [raw-input]
  raw-input)

(defn solve [grid]
  grid)

(defn part1 
  ([] (part1 "day15/input.txt"))
  ([file-name]
  (-> file-name
      (utils/read-file)
      (parse-input)
      (solve))))