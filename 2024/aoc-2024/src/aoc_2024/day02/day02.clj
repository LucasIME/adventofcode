(ns aoc-2024.day02.day02
  (:require [aoc-2024.utils.utils :as utils]
             [clojure.string :as str]))

(defn to-int-array [input]
  (map  #(Integer/parseInt %1) input))

(defn is-all-increasing [input] 
  (apply > input))
(defn is-all-decreasing [input] 
  (apply < input))

(defn adjacent-differ-by [input mini maxi]
  (let [paired (partition 2 1 input)
        resp (every? #(
                       let [abs_v (Math/abs (- (first %1) (second %1)))] 
                         (and (>= abs_v mini) (<= abs_v maxi)))
                     paired)] 
    resp))

(defn is-safe [input]
  (and 
   (or (is-all-increasing input) (is-all-decreasing input))
   (adjacent-differ-by input 1 3)))

(defn input-without-nth [input n]
  (let [before (take n input)
        after (drop (inc n) input)]
    (concat before after)))

(defn comb-removing-one [input]
  (let [combinations (for [i (range (count input))]
                      (input-without-nth input i)
                      )]
    (concat (list input) combinations)))

(defn is-lenient-safe [input]
  (some is-safe (comb-removing-one input)))

(defn parse-input [raw_lines]
  (->> raw_lines
       (map #(str/split %1  #" "))
       (map  to-int-array)))

(defn solve [lines]
  (->> lines
       (filter is-safe)
       (count)))

(defn part1 
  ([] (part1 "day02/input.txt"))
  ([fileName]
   (-> fileName
       (utils/read-file-lines)
       (parse-input)
       (solve))))

(defn solve2 [lines]
  (->> lines
       (filter is-lenient-safe)
       (count)))

(defn part2 
  ([] (part2 "day02/input.txt"))
  ([fileName]
   (-> fileName
       (utils/read-file-lines)
       (parse-input)
       (solve2))))