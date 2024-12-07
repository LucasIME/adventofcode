(ns aoc-2024.day07.day07
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.string :as str]))

(defn parse-line [line]
  (let [[raw_target raw_nums_str] (str/split line #": ")
        raw_nums (str/split raw_nums_str #" ")]
    [(Integer/parseInt raw_target)
     (map #(Integer/parseInt %1) raw_nums)]))

(defn parse-input [lines]
  (map parse-line lines)) 

(defn is-solvable? [[target nums]]
  (loop [acc (first nums), remaining (drop 1 nums)]
    (cond
      (empty? remaining) (= acc target)
      :else (or
             (recur (+ acc (first remaining)) (drop 1 remaining))
             (recur (* acc (first remaining)) (drop 1 remaining))
             ))))

(defn solve [equations]
  (->> equations
       (filter is-solvable?)
       (map first)
       (reduce +)))

(defn part1 
  ([] (part1 "day07/input.txt"))
  ([fileName]
  (-> fileName
      (utils/read-file-lines)
      (parse-input)
      (solve))))

(part1 "day07/ex1.txt")