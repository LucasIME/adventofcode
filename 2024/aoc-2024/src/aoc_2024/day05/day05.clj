(ns aoc-2024.day05.day05
  (:require [aoc-2024.utils.utils :as utils]
             [clojure.string :as str]))

(defn parse-rules [raw_rules] 
  (let [raw_lines (str/split raw_rules #"\n")
        raw_pairs (map #(str/split %1 #"\|") raw_lines)
        pairs (map 
               (fn [raw_pair_vec] (map #(Integer/parseInt %1) raw_pair_vec) )
               raw_pairs)
        rev_pairs (map reverse pairs)]
   (update-vals (group-by first rev_pairs) #(map second %1))
    ))

(defn parse-updates [raw_sequences]
  (let [lines (str/split raw_sequences #"\n")
        split_lines (map #(str/split %1 #",") lines)]
        (map 
         (fn [array] (map #(Integer/parseInt %1) array)) 
         split_lines)))

(defn parse-input [raw_text]
  (let [[raw_rules raw_sequences] (str/split raw_text #"\n\n")
        rules (parse-rules raw_rules)
        updates (parse-updates raw_sequences)]
    [rules updates])) 

(first (parse-input (utils/read-file "day05/ex1.txt")))

(defn is-valid? [rules entry]
  (loop [to_process entry, is_valid? true, seen_so_far #{}]
    (cond
      (empty? to_process) is-valid?
      (not is-valid?) false
      :else (let [[head & tail] to_process
                  allowed_before ]
              )
      )
    )
  )

(defn middle-elem [list]
  (nth list (quot (count list) 2)))

(middle-elem [1 2 3 4])

(defn solve [[rules entries]]
  (let [valid_entries (filter #(is-valid? rules %1) entries)]
    (->> valid_entries
        (map middle-elem)
        (reduce +))))
  
(defn part1 
  ([] (part1 "day05/input.txt"))
  ([fileName]
  (-> fileName
      (utils/read-file)
      (parse-input)
      (solve))))

(let [[x & y] '(1 2 3 4 5 6)]
  [x y])

(part1)