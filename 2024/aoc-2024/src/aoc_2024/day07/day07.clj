(ns aoc-2024.day07.day07
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.string :as str]))

(defn parse-line [line]
  (let [[raw_target raw_nums_str] (str/split line #": ")
        raw_nums (str/split raw_nums_str #" ")]
    [(Long/parseLong raw_target)
     (map #(Long/parseLong %1) raw_nums)]))

(defn parse-input [lines]
  (map parse-line lines)) 

(defn is-solvable? [[target nums]]
  (letfn [(helper [acc remaining]
    (cond
      (empty? remaining) (= acc target)
      :else (or
             (helper (+ acc (first remaining)) (drop 1 remaining))
             (helper (* acc (first remaining)) (drop 1 remaining))
             )))]
    (helper (first nums) (drop 1 nums))))

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

(defn num-concat [n1 n2]
  (Long/parseLong (str n1 n2)))

(defn is-solvable2? [[target nums]]
  (letfn [(helper [acc remaining]
    (cond
      (empty? remaining) (= acc target)
      :else (or
             (helper (+ acc (first remaining)) (drop 1 remaining))
             (helper (* acc (first remaining)) (drop 1 remaining))
             (helper (num-concat acc (first remaining)) (drop 1 remaining))
             )))]
    (helper (first nums) (drop 1 nums))))

(defn solve2 [equations]
  (->> equations
       (filter is-solvable2?)
       (map first)
       (reduce +)))

(defn part2 
  ([] (part2 "day07/input.txt"))
  ([file-name]
  (-> file-name
      (utils/read-file-lines)
      (parse-input)
      (solve2))))
