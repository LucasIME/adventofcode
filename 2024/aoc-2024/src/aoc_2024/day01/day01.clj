(ns aoc-2024.day01.day01
  (:require [aoc-2024.utils.utils :as utils]
             [clojure.string :as str]))


(defn parse-input [lines]
  (let [paired_lines (map #(str/split %1 #"\s+") lines)
        l1 (map #(Integer/parseInt (first %1)) paired_lines)
        l2 (map #(Integer/parseInt (second %1)) paired_lines)
        resp (list l1 l2)] 
    resp))

(defn solve [[l1 l2]]
  (let [sorted1 (sort l1)
        sorted2 (sort l2)
        zipped (map vector sorted1 sorted2)
        diff (map (fn [[v1 v2]] (Math/abs (- v2 v1))) zipped)
        resp (reduce + diff)]
    resp))

(defn part1 
  ([] (part1 "day01/input.txt"))
  ([fileName]
  (-> fileName
      (utils/read-file-lines)
      (parse-input)
      (solve))))

(defn solve2 [[l1 l2]]
  (let [b_freq (frequencies l2)
        a_similarity (map #(* %1 (get b_freq %1 0)) l1)] 
    (reduce + a_similarity)))

(defn part2 
  ([] (part2 "day01/input.txt"))
  ([fileName]
   (-> fileName
       (utils/read-file-lines)
       (parse-input)
       (solve2))))