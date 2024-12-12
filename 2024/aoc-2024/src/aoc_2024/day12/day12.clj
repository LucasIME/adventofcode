(ns aoc-2024.day12.day12
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.string :as str]))

(defn parse-input [raw_input]
  raw_input)

(defn solve [input]
  input)

(defn part1 
  ([] (part1 "day11/input.txt"))
  ([file-name]
  (-> file-name
      (utils/read-file)
      (parse-input)
      (solve))))