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

(defn manhattan-dist [[row col] [row2 col2]]
  (+ (Math/abs (- row row2)) (Math/abs (- col col2))))

(defn get-cheat-neighs [grid [start-row start-col] known-dists radius]
  (for [row (range (- start-row radius) (+ start-row radius 1))
        col (range (- start-col radius) (+ start-col radius 1))
        :let [dist (manhattan-dist [start-row start-col] [row col])]
        :when (and
               (not= [row col] [start-row start-col])
               (contains? known-dists [row col])
               (not= (get-in grid [row col]) "#")
               (<= dist radius))]
    [[row col] dist]))

(defn collect-cheats [grid best-path known-dists radius]
  (loop [pending best-path, cheats []]
    (cond
      (empty? pending) cheats
      :else (let [cur (first pending)
                  best-path-size (count best-path)
                  dist-to-source (- best-path-size (known-dists cur) 1)
                  neighs-and-dist (get-cheat-neighs grid cur known-dists radius)
                  new-cheats (map (fn [[cheat-neigh cheat-dist]]
                                    (let [new-dist (+ dist-to-source cheat-dist (get known-dists cheat-neigh))]
                                      (- best-path-size new-dist 1))) ;; Not sure why I need the -1 here
                                  neighs-and-dist)
                  good-cheats (filter #(> %1 0) new-cheats)
                  new-cheat-out (reduce (fn [acc new] (conj acc new)) cheats good-cheats)]
              (recur (rest pending) new-cheat-out)))))

(defn solve [[grid start target-pos] radius min]
  (let [[shortest prev-map] (shortest-path grid start target-pos)
        best-path (get-path prev-map target-pos)
        position-to-target-dist (pos-to-dist best-path)
        all-cheats (collect-cheats grid best-path position-to-target-dist radius)
        better-than-minimum-cheats (filter #(>= %1 min) all-cheats)]
    (count better-than-minimum-cheats)))

(defn part1
  ([] (part1 "day20/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file-lines)
       (parse-input)
       (solve 2 100))))

(defn part2
  ([] (part2 "day20/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file-lines)
       (parse-input)
       (solve 20 100))))
