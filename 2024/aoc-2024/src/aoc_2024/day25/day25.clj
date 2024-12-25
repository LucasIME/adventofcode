(ns aoc-2024.day25.day25
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.string :as str]))

(defn transpose [matrix]
  (apply mapv vector matrix))

(defn count-starting [s target]
  (loop [cur s, target target, out 0]
    (cond
      (empty? s) out
      (= (first cur) target) (recur (rest cur) target (inc out))
      :else out)))

(defn parse-block [raw-block]
  (let [raw-lines (str/split raw-block #"\n")
        char-matrix (map #(str/split %1 #"") raw-lines)
        type (if  (= (first (first raw-lines)) \#) :lock :key)
        matrix-without-first-and-last (->> char-matrix
                                            (drop 1)
                                            (drop-last 1))
        transposed (transpose matrix-without-first-and-last)
        target (if (= type :lock) "#" ".")
        num-v (map #(count-starting %1 target) transposed)
        real-num-v (map #(if (= type :key) (- 5 %) %) num-v)]
    [type real-num-v]))

(defn parse-input [raw-input]
  (let [raw-blocks (str/split raw-input #"\n\n")
        blocks (map parse-block raw-blocks)
        lock-and-keys-map (group-by first blocks)
        locks (map second (get lock-and-keys-map :lock))
        keys (map second (get lock-and-keys-map :key))]
    [locks keys]))

(defn matches? [lock key]
  (let [sum (map + lock key)]
    (every? #(<= % 5) sum)))

(defn solve [[locks keys]]
  (let [matching (for [lock locks
                       key keys
                       :when (matches? lock key)] [lock key])]
    (count matching)))

(defn part1
  ([] (part1 "day25/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file)
       (parse-input)
       (solve))))