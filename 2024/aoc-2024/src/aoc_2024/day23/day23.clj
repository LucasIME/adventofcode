(ns aoc-2024.day23.day23
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.string :as str]))

(defn parse-input [lines]
  (let [pairs (map #(str/split %1 #"-") lines)
        graph (reduce (fn [acc [p1 p2]]
                        (let [cur-p1 (get acc p1 [])
                              cur-p2 (get acc p2 [])
                              acc-with-p1 (assoc acc p1 (conj cur-p1 p2))
                              acc-with-p1-and-p2 (assoc acc-with-p1 p2 (conj cur-p2 p1))]
                          acc-with-p1-and-p2)) {} pairs)]
    graph))

(defn get-triplets [graph key]
  (let [neighs (get graph key)
        triplets (for [i (range (count neighs))
                       j (range (inc i) (count neighs))
                       :let [n1 (nth neighs i)
                             n2 (nth neighs j)]
                       :when (contains? (set (get graph n1)) n2)]
                   (sort [key n1 n2]))]
    triplets))

(defn get-connected-triplets [graph]
  (let [keys-starting-with-t (filter #(str/starts-with? %1 "t") (keys graph))
        all-triplets (map #(get-triplets graph %1) keys-starting-with-t)
        triplet-set (set (mapcat identity all-triplets))]
    (count triplet-set)))

(defn solve [graph]
  (get-connected-triplets graph))

(defn part1
  ([] (part1 "day23/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file-lines)
       (parse-input)
       (solve))))