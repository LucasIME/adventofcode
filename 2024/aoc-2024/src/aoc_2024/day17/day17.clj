(ns aoc-2024.day17.day17
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.string :as str]))

(defn parse-register [raw-line]
  (let [[_ raw-name-and-val] (str/split raw-line #"Register ")
        [name raw-val] (str/split raw-name-and-val #": ")]
    [name (Integer/parseInt raw-val)]))

(defn parse-registers [raw-registers]
  (let [lines (str/split raw-registers #"\n")
        registers (map parse-register lines)]
    (into {} registers)))

(defn parse-input [raw-input]
  (let [[raw-registers raw-program] (str/split raw-input #"\n\n")
        [_ raw-p-list] (str/split raw-program #"Program: ")
        registers (parse-registers raw-registers)
        p-list (map #(Integer/parseInt %1) (str/split raw-p-list #","))]
    [registers p-list]))

(defn solve [input]
  input)

(defn part1 
  ([] (part1 "day17/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file)
       (parse-input)
       (solve))))