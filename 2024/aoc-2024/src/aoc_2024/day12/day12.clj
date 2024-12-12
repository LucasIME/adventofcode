(ns aoc-2024.day12.day12
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.set :as set]))

(defn parse-input [lines]
  (utils/to-char-matrix lines))

(def directions [[1 0] [-1 0] [0 1] [0 -1]])

(defn get-neigh-pos [row col]
  (map (fn [[dr dc]] [(+ row dr) (+ col dc)]) directions))

(defn get-valid-neighs [grid row col]
  (filter #(and (not= nil (get-in grid %1))
                (= (get-in grid %1) (get-in grid [row col])))
          (get-neigh-pos row col)))

(defn explore 
  ([grid [row col]] (explore grid row col #{}))
  ([grid row col visited]
   (if (contains? visited [row col]) visited 
     (let [valid-neighs (get-valid-neighs grid row col)
           visited-with-me (conj visited [row col])]
       
       (set/union visited-with-me 
                  (reduce set/union 
                          (map #(explore grid (first %1) (second %1) visited-with-me) 
                               valid-neighs)))))))

(defn perimiter-pos [grid [row col]]
  (let [cur-val (get-in grid [row col])]
    (count  (filter #(not= cur-val (get-in grid %1))
          (get-neigh-pos row col)))))

(defn perimeter [grid island] 
  (let [result (reduce + (map #(perimiter-pos grid %1) island))] 
    result))

(defn island-score [grid island]
  (* (count island)
     (perimeter grid island)))

(defn explore-grid [grid]
  (let [positions (for [row (range (count grid))
                        col (range (count (get grid row)))]
                    [row col])]
    (loop [to-visit positions, all-visited #{}, score 0]
      (cond 
        (empty? to-visit) score
        (contains? all-visited (first to-visit)) (recur (rest to-visit) all-visited score)
        :else (let [island (explore grid (first to-visit))]
                (recur (rest to-visit) (set/union all-visited (set island)) (+ score (island-score grid island))))))))

(defn solve [grid]
  (explore-grid grid))

(defn part1 
  ([] (part1 "day12/input.txt"))
  ([file-name]
  (-> file-name
      (utils/read-file-lines)
      (parse-input)
      (solve))))
