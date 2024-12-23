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

(defn power-set [coll]
  (reduce (fn [acc x]
            (concat acc (map #(conj % x) acc)))
          [[]]
          coll))

(defn is-connected-set? [graph key-set]
  (every? (fn [key]
            (let [other-keys (disj key-set key)]
              (every? (fn [key2]
                        (contains? (get graph key) key2)) other-keys))) key-set))

(defn get-best-connected-set [graph]
  (let [key-to-powerset (map (fn [[k v]] [k (power-set (conj v k))]) graph)
        key-to-connected-sets (map (fn [[k v]] [k (filter #(is-connected-set? graph (set %)) v)])
                                   key-to-powerset)
        all-valid-connected-set (mapcat (fn [[k v]] v) key-to-connected-sets)
        largest-valid-connected-set (apply max-key count all-valid-connected-set)]
    largest-valid-connected-set))

(defn solve2 [graph]
  (let [graph-set (into {} (map (fn [[k v]] [k (set v)]) graph))
        best-connected-set (get-best-connected-set graph-set)
        name (str/join "," (sort best-connected-set))]
    name))

(defn part2
  ([] (part2 "day23/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file-lines)
       (parse-input)
       (solve2))))