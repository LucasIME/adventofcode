(ns aoc-2024.day03.day03
  (:require [aoc-2024.utils.utils :as utils]
             [clojure.string :as str]))

(def mul-regex #"mul\(\d{1,3},\d{1,3}\)")
(def mul-regex-start (re-pattern (str "^" mul-regex)))

(defn parse-mul-match [input]
  (-> input
   (#(subs %1 4 (dec (count %1))))
   (str/split #",")
   (#(list (Integer/parseInt (first %1)) (Integer/parseInt (second %1))))))

(defn parse-input [raw_input]
  (->> raw_input
       (re-seq mul-regex)
       (map parse-mul-match)))

(defn solve [pairs]
  (->> pairs 
       (map #(* (first %1) (second %1))) 
       (reduce +)))

(defn part1 
  ([] (part1 "day03/input.txt"))
  ([fileName]
  (-> fileName
      (utils/read-file)
      (parse-input)
      (solve))))

(defn try-extract-mul-regex [input]
  (let [maybe_match (re-find mul-regex-start input)]
    (if (nil? maybe_match) '(nil nil) (list maybe_match (parse-mul-match maybe_match)))))

(defn parse-with-dos 
  ([input] (parse-with-dos input true '()))
  ([input should_accept out] 
  (loop [input input, should_accept should_accept, out out]
    (cond
        (empty? input) out
        (str/starts-with? input "do()") (recur (subs input 4) true out)
        (str/starts-with? input "don't()") (recur (subs input 7) false out)
        should_accept (let [[maybe_raw maybe_nums] (try-extract-mul-regex input)]
                          (if (nil? maybe_raw)
                            (recur (subs input 1) should_accept out)
                            (recur (subs input (count maybe_raw)) should_accept (conj out maybe_nums))
                          ))
        :else (recur (subs input 1) should_accept out)))))

(defn part2 
  ([] (part2 "day03/input.txt"))
  ([fileName]
   (-> fileName
   (utils/read-file)
   (parse-with-dos)
   (solve))))