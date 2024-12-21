(ns aoc-2024.day21.day21
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.string :as str]))

(def directions [[-1 0] [0 1] [1 0] [0 -1]])

(def num-grid [["7" "8" "9"]
               ["4" "5" "6"]
               ["1" "2" "3"]
               [nil "0" "A"]])

(defn num-grid-symbol-to-pos [symbol]
  (cond
    (= symbol "7") [0 0]
    (= symbol "8") [0 1]
    (= symbol "9") [0 2]
    (= symbol "4") [1 0]
    (= symbol "5") [1 1]
    (= symbol "6") [1 2]
    (= symbol "1") [2 0]
    (= symbol "2") [2 1]
    (= symbol "3") [2 2]
    (= symbol "0") [3 1]
    (= symbol "A") [3 2]))

(def arrow-grid [[nil "^" "A"]
                 ["<" "v" ">"]])

(defn arrow-grid-symbol-to-pos [symbol]
  (cond
    (= symbol "^") [0 1]
    (= symbol "A") [0 2]
    (= symbol "<") [1 0]
    (= symbol "v") [1 1]
    (= symbol ">") [1 2]))

(defn add-pos [[row1 col1] [row2 col2]]
  [(+ row1 row2) (+ col1 col2)])

(defn get-direct-neighs-with-dir [grid pos]
  (let [neighs-and-dir (map #(vector (add-pos pos %1) %1) directions)
        valid-neighs-and-dir (filter #(not= (get-in grid (first %1)) nil) neighs-and-dir)]
    valid-neighs-and-dir))

(defn dir-char [dir]
  (cond
    (= dir [-1 0]) "^"
    (= dir [0 1]) ">"
    (= dir [1 0]) "v"
    (= dir [0 -1]) "<"))

(defn get-shortest-paths-no-press [grid start-pos target-val]
  (loop [queue (conj (clojure.lang.PersistentQueue/EMPTY) [start-pos 0 []])
         visited #{}
         found-paths []]
    (let [[cur dist path-to-here] (peek queue)]
      (cond
        (empty? queue) found-paths
        (and (not (empty? found-paths))
             (> dist (count (first found-paths)))) found-paths
        (= (get-in grid cur) target-val) (recur (pop queue)
                                                visited
                                                (conj found-paths path-to-here))
        :else (let [new-visited (conj visited cur)
                    neighs-with-dir (get-direct-neighs-with-dir grid cur)
                    new-q (reduce (fn [acc [neigh dir]] (conj acc [neigh (inc dist) (conj path-to-here (dir-char dir))]))
                                  (pop queue)
                                  neighs-with-dir)]
                (recur new-q new-visited found-paths))))))

(defn dirpath [grid from to pos-getting-fn]
  (let [paths (get-shortest-paths-no-press grid (pos-getting-fn from) to)
        press-paths (map #(vec (concat ["A"] %1 ["A"])) paths)]
    press-paths))

(def get-arrow-cost-indirection
  (memoize (fn [sequence level]
             (cond
               (= level 0) (dec (count sequence))
               :else (let [pairs (partition 2 1 sequence)
                           good-paths-between-pairs (map (fn [[from to]]
                                                           (dirpath arrow-grid
                                                                    from
                                                                    to
                                                                    arrow-grid-symbol-to-pos))
                                                         pairs)

                           costs-per-path-for-pair (map (fn [paths]
                                                          (map #(get-arrow-cost-indirection
                                                                 %1
                                                                 (dec level))
                                                               paths))
                                                        good-paths-between-pairs)
                           good-paths-cost (map #(apply min %1) costs-per-path-for-pair)
                           cost (reduce + good-paths-cost)]
                       cost)))))

(defn get-grid-cost-indirection [line level]
  (let [pairs (partition 2 1 (conj line "A"))
        good-paths-between-pairs (map (fn [[from to]]
                                        (dirpath num-grid
                                                 from
                                                 to
                                                 num-grid-symbol-to-pos))
                                      pairs)
        costs-per-path-for-pair (map (fn [paths]
                                       (map #(get-arrow-cost-indirection %1 level)
                                            paths))
                                     good-paths-between-pairs)
        good-paths-cost (map #(apply min %1) costs-per-path-for-pair)
        cost (reduce + good-paths-cost)]
    cost))

(defn solve-line [line n]
  (let [int-line (Integer/parseInt (first (str/split line #"A")))
        line-path (apply list (str/split line #""))
        shortest-path (get-grid-cost-indirection line-path n)]
    (* int-line shortest-path)))

(defn solve [lines n]
  (->> lines
       (map #(solve-line %1 n))
       (reduce +)))

(defn part1
  ([] (part1 "day21/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file-lines)
       (solve 2))))

(defn part2
  ([] (part2 "day21/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file-lines)
       (solve 25))))
