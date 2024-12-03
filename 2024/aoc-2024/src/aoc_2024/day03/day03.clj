(ns aoc-2024.day03.day03
  (:require [aoc-2024.utils.utils :as utils]
             [clojure.string :as str]))

(def mul-regex #"mul\(\d{1,3},\d{1,3}\)")
(def mul-regex-start (re-pattern (str "^" mul-regex)))

(defn parse-input [raw_input]
  (let [raw_mult_pairs (re-seq mul-regex raw_input)
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

(defn parse-mul-match [input]
  ( -> input
   (#(subs %1 4 (dec (count %1))))
   (str/split #",")
   (#(list (Integer/parseInt (first %1)) (Integer/parseInt (second %1)) ) )
   ))


(defn try-extract-mul-regex [input]
  (let [maybe_match (re-find mul-regex-start input)]
    (if (nil? maybe_match) '(nil nil) (list maybe_match (parse-mul-match maybe_match)))))

(defn parse-with-dos 
  ([input] (parse-with-dos input true '()))
  ([input should_accept out] 
  (loop [input input, should_accept should_accept, out out]
    (cond
        (str/starts-with? input "do()") (recur (subs input 4) true out)
        (str/starts-with? input "don't()") (recur (subs input 7) false out)
        (<= (count input) 6) out
        should_accept (let [[maybe_raw maybe_nums] (try-extract-mul-regex input)]
                          (if (nil? maybe_raw)
                            (recur (subs input 1) should_accept out)
                            (recur (subs input (count maybe_raw)) should_accept (conj out maybe_nums))
                          ))
        :else (recur (subs input 1) should_accept out)))))

(defn part2 
  ([] (part2 "day03/input.txt"))
  ([fileName]
    (let [raw_input  (utils/read-file fileName)
          input (parse-with-dos raw_input)
          resp (solve input)]
          resp)))