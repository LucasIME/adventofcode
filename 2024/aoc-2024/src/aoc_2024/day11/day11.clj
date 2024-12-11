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

(defn pair-list-to-map [pair-list]
  (reduce (fn [accumulator [k v]] 
            (update accumulator k (fnil + 0) v)) 
          {} 
          pair-list))

(defn blink [input]
  (let [new-values-and-times (mapcat (fn [[k freq]] 
                                    (map (fn [new-stone] [new-stone freq])  
                                         (blink-stone k))) 
                                  input)] 
    (pair-list-to-map new-values-and-times)))

(defn solve [input n]
  (loop [stones (frequencies input), remaining n]
    (cond 
      (= remaining 0) (reduce + (map second stones)) 
      :else (recur (blink stones) (dec remaining)))))

(defn part1 
  ([] (part1 "day11/input.txt" 25))
  ([file-name n]
  (-> file-name
      (utils/read-file)
      (parse-input)
      (solve n))))