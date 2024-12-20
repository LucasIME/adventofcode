(ns aoc-2024.day20.day20
  (:require [aoc-2024.utils.utils :as utils]))

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
  (loop [queue (conj (clojure.lang.PersistentQueue/EMPTY) [start 0 nil])
         visited #{}
         prev-map {}]
    (let [[cur dist before] (peek queue)]
      (cond
        (= cur end) [dist (assoc prev-map cur before)]
        (empty? queue) Integer/MAX_VALUE
        (contains? visited cur) (recur (pop queue) visited prev-map)
        :else (let [new-visited (conj visited cur)
                    neighs (get-direct-neighs grid cur)
                    new-q (reduce (fn [acc neigh] (conj acc [neigh (inc dist) cur])) (pop queue) neighs)
                    new-prev-map (if (not= nil before) (assoc prev-map cur before) prev-map)]
                (recur new-q new-visited new-prev-map))))))

(defn get-path [prev-map target-pos]
  (loop [cur target-pos
         path []]
    (if (nil? cur)
      path
      (recur (get prev-map cur) (conj path cur)))))

(defn pos-to-dist [path]
  (let [pos-and-dist-pairs (map-indexed (fn [idx pos] [pos idx]) path)]
    (into {} pos-and-dist-pairs)))

(defn get-two-next-in-dir [pos dir]
  (let [p1 (add-pos pos dir)
        p2 (add-pos p1 dir)]
    [p1 p2]))

(defn get-cheat-neighs [grid pos known-dists]
  (let [two-next-vec (map #(get-two-next-in-dir pos %1) directions)
        cheat-neighs (filter (fn [[p1 p2]]
                               (and
                                (= (get-in grid p1) "#")
                                (not= (get-in grid p2) "#")
                                (contains? known-dists p2)))
                             two-next-vec)]
    (map #(second %1) cheat-neighs)))

(defn collect-cheats [grid best-path known-dists]
  (loop [pending best-path, cheats []]
    (cond
      (empty? pending) cheats
      :else (let [cur (first pending)
                  best-path-size (count best-path)
                  dist-to-source (- best-path-size (known-dists cur) 1)
                  neighs (get-cheat-neighs grid cur known-dists)
                  new-cheats (map (fn [cheat-neigh]
                                    (let [new-dist (+ dist-to-source 2 (get known-dists cheat-neigh))]
                                      (- best-path-size new-dist 1))) ;; Not sure why I need the -1 here
                                  neighs)
                  good-cheats (filter #(> %1 0) new-cheats)
                  new-cheat-out (reduce (fn [acc new] (conj acc new)) cheats good-cheats)]
              (recur (rest pending) new-cheat-out)))))

(defn solve [[grid start target-pos]]
  (let [[shortest prev-map] (shortest-path grid start target-pos)
        best-path (get-path prev-map target-pos)
        position-to-target-dist (pos-to-dist best-path)
        all-cheats (collect-cheats grid best-path position-to-target-dist)
        better-than-100-cheats (filter #(>= %1 100) all-cheats)]
    (count better-than-100-cheats)))

(defn part1
  ([] (part1 "day20/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file-lines)
       (parse-input)
       (solve))))
