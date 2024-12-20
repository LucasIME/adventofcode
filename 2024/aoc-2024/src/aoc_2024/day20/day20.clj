(ns aoc-2024.day20.day20
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.set :as set]))

(def directions [[-1 0] [0 1] [1 0] [0 -1]])

(defn get-target-pos [grid target]
  (for [row (range (count grid))
        col (range (count (get grid row)))
        :when (= (get-in grid [row col]) target)]
    [row col]))

(defn parse-input [lines]
  (let [char-matrix (utils/to-char-matrix lines)
        [start-pos] (get-target-pos char-matrix "S")
        [end-pos] (get-target-pos char-matrix "E")]
    [char-matrix start-pos end-pos]))

(defn add-pos [[row1 col1] [row2 col2]]
  [(+ row1 row2) (+ col1 col2)])

(defn get-direct-neighs [grid pos]
  (let [neighs (map #(add-pos pos %1) directions)
        valid-neighs (filter #(not= (get-in grid %1) "#") neighs)]
    valid-neighs))

(defn shortest-path [grid start end]
  (loop [queue (conj (clojure.lang.PersistentQueue/EMPTY) [start 0])
         visited #{}]
    (let [[cur dist] (peek queue)]
      (cond
        (= cur end) dist
        (empty? queue) Integer/MAX_VALUE
        (contains? visited cur) (recur (pop queue) visited)
        :else (let [new-visited (conj visited cur)
                    neighs (get-direct-neighs grid cur)
                    new-q (reduce (fn [acc neigh] (conj acc [neigh (inc dist)])) (pop queue) neighs)]
                (recur new-q new-visited))))))

(defn generate-all-cheat-grids [grid]
  (for [row (range (count grid))
        col (range (count (get grid row)))
        :when (= (get-in grid [row col]) "#")]
    (let [new-grid (assoc-in grid [row col] ".")]
      new-grid)))

(defn solve [[grid start target-pos]]
  (let [shortest (shortest-path grid start target-pos)
        all-cheat-grids (generate-all-cheat-grids grid)
        all-shorted-paths (map #(shortest-path %1 start target-pos) all-cheat-grids)
        good-cheats (filter #(<= %1 (- shortest 100)) all-shorted-paths)]
    ;; (println start target-pos)
    ;; (println shortest)
    ;; (println all-shorted-paths (frequencies (map #(- shortest %1) all-shorted-paths)))
    (println (count good-cheats))
    (count good-cheats)))

(defn part1
  ([] (part1 "day20/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file-lines)
       (parse-input)
       (solve))))

(part1 "day20/ex1.txt")
