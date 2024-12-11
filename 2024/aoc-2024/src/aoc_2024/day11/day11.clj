(ns aoc-2024.day11.day11
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.string :as str]))

(defn parse-input [raw_input]
  (map #(Integer/parseInt %1) (str/split raw_input #" ")))

(defn split [n digits] 
  (let [half-digits (int (/ digits 2))
        left (int (/ n (Math/pow 10 half-digits)))
        right (rem n (int (Math/pow 10 half-digits)))]
    [left right]))

(defn blink-stone [stone]
  (let [digits (if (= stone 0)
                 1
                 (int (inc (Math/log10 stone))))]
    (cond 
      (= 0 stone) [1]
      (= (rem digits 2) 0) (split stone digits)
      :else [(* 2024 stone)])))

(defn blink [input]
  (mapcat blink-stone input))

(defn solve [input n]
  (loop [stones input, remaining n]
    (cond 
      (= remaining 0) (count stones) 
      :else (recur (blink stones) (dec remaining)))))

(defn part1 
  ([] (part1 "day11/input.txt" 25))
  ([file-name n]
  (-> file-name
      (utils/read-file)
      (parse-input)
      (solve n))))
