(ns aoc-2024.day18.day18
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.string :as str]))

(defn parse-input [lines]
  (let [raw-coords (map #(str/split %1 #",") lines)
        coords (map #(map (fn [n] (Integer/parseInt n)) %1) raw-coords)
        row-cols (map (fn [[x y]] [y x]) coords)]
  row-cols))

(def directions [[-1 0] [0 1] [1 0] [0 -1]])

(defn direct-neighs [[row col]]
  (map (fn [[dr dc]] [(+ row dr) (+ col dc)]) directions))

(defn is-valid? [[row col] rows cols blocked]
  (and
   (>= row 0)
   (>= col 0)
   (< row rows)
   (< col cols)
   (not (contains? blocked [row col]))))

(defn get-valid-neighs [[row col] rows cols blocked]
  (let [raw-neighs (direct-neighs [row col])]
    (filter #(is-valid? %1 rows cols blocked) raw-neighs)))

(defn bfs [start rows cols blocked]
  (loop [queue (conj (clojure.lang.PersistentQueue/EMPTY) [start 0])
         visited blocked]
    (let [[cur dist] (peek queue)]
      (cond 
        (= cur [(dec rows) (dec cols)]) dist
        (empty? queue) Integer/MAX_VALUE
        (contains? visited cur) (recur (pop queue) visited)
        :else (let [new-visited (conj visited cur)
                    neighs (get-valid-neighs cur rows cols new-visited)
                    new-q (reduce (fn [acc neigh] (conj acc [neigh (inc dist)])) (pop queue) neighs)]
                (recur new-q new-visited))))))

(defn solve [fall rows cols target-time]
  (let [blocked (set (take target-time fall))]
    (bfs [0 0] rows cols blocked)))

(defn part1 
  ([] (part1 "day18/input.txt" 71 71 1024))
  ([file-name rows cols target-time]
   (-> file-name
       (utils/read-file-lines)
       (parse-input)
       (solve rows cols target-time))))

(defn solve2 [fall rows cols]
  (loop [n 1]
    (let [raw-blocked (take n fall)
          blocked (set raw-blocked)
          dist (bfs [0 0] rows cols blocked)
          can-get (not= dist Integer/MAX_VALUE)]
      (if can-get 
        (recur (inc n)) 
        (let [[row col] (last raw-blocked)
              aoc-coords [col row]
              formatted-resp (str/join "," aoc-coords)]
          formatted-resp)))))

(defn part2 
  ([] (part2 "day18/input.txt" 71 71))
  ([file-name rows cols]
   (-> file-name
       (utils/read-file-lines)
       (parse-input)
       (solve2 rows cols))))