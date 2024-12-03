(ns aoc-2024.day03.day03
  (:require [aoc-2024.utils.utils :as utils]
             [clojure.string :as str]))


(defn parse-input [raw_input]
  (let [raw_mult_pairs (re-seq #"mul\(\d{1,3},\d{1,3}\)"  raw_input)
        num_with_comma_list (map #(subs %1 4 (dec (count %1))) raw_mult_pairs)
        raw_num_str_pair_list (map #(str/split %1 #",") num_with_comma_list)
        num_pair_list (map #(list (Integer/parseInt (first %1)) (Integer/parseInt (second %1)) ) raw_num_str_pair_list)
        resp num_pair_list]
    resp))

(defn solve [pairs]
  (let [mults  (map #(* (first %1) (second %1)) pairs)
        resp (reduce + mults)]
    resp))

(defn part1 
  ([] (part1 "day03/input.txt"))
  ([fileName]
    (let [raw_input  (utils/read-file fileName)
          input (parse-input raw_input)
          resp (solve input)]
          resp
      )))