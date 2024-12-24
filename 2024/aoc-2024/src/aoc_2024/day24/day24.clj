(ns aoc-2024.day24.day24
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.string :as str]
            [clojure.core.match :as match]))

(defn parse-nums [raw-nums]
  (let [raw-lines (str/split raw-nums #"\n")
        symbol-and-num-list (map (fn [line]
                                   (let [[symbol raw-n] (str/split line #": ")]
                                     [symbol {:val (Integer/parseInt raw-n)}])) raw-lines)]
    symbol-and-num-list))

(defn parse-gates [raw-gates]
  (let [raw-lines (str/split raw-gates #"\n")
        gates (map (fn [line]
                     (let [[symbol1 op symbol2 _arrow output] (str/split line #" ")]
                       [output {:symbol1 symbol1
                                :op op
                                :symbol2 symbol2}])) raw-lines)]

    gates))

(defn parse-input [raw-input]
  (let [[raw-nums raw-gates] (str/split raw-input #"\n\n")
        symbol-and-num-list (parse-nums raw-nums)
        gates (parse-gates raw-gates)]
    (into {} (concat symbol-and-num-list gates))))


(defn do-op [op val1 val2]
  (case op
    "AND" (bit-and val1 val2)
    "OR" (bit-or val1 val2)
    "XOR" (bit-xor val1 val2)))


(defn get-val [graph entry]
  (match/match [(get graph entry)]
    [nil] nil
    [{:val val}] val
    [{:symbol1 symbol1 :op op :symbol2 symbol2}] (do-op op
                                                        (get-val graph symbol1)
                                                        (get-val graph symbol2))))

(defn bit-to-num [bits]
  (reduce (fn [acc [i bit]] (+ acc (bit-shift-left bit i)))
          0
          (map-indexed (fn [i n] [i n]) bits)))

(defn solve [graph]
  (let [all-keys (keys graph)
        sorted-z-keys (sort (filter #(str/starts-with? %1 "z") all-keys))
        bit-vals (map #(get-val graph %1) sorted-z-keys)
        n (bit-to-num bit-vals)]
    n))

(defn part1
  ([] (part1 "day24/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file)
       (parse-input)
       (solve))))
