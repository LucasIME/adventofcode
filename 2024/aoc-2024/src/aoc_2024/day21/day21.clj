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

(defn get-shortest-paths [grid start-pos target-val]
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
                                                (conj found-paths (conj path-to-here "A")))
        :else (let [new-visited (conj visited cur)
                    neighs-with-dir (get-direct-neighs-with-dir grid cur)
                    new-q (reduce (fn [acc [neigh dir]] (conj acc [neigh (inc dist) (conj path-to-here (dir-char dir))]))
                                  (pop queue)
                                  neighs-with-dir)]
                (recur new-q new-visited found-paths))))))

(defn merge-paths [paths-so-far new-paths]
  (for [path1 paths-so-far
        path2 new-paths]
    (into path1 path2)))

(defn get-shortest-paths-for-sequence [grid start sequence pos-getting-fn]
  (loop [cur-pos start
         pending sequence
         found-paths [[]]]
    (cond
      (empty? pending) found-paths
      :else
      (let [cur-target (first pending)
            paths (get-shortest-paths grid cur-pos cur-target)
            new-pos (pos-getting-fn cur-target)
            new-paths (merge-paths found-paths paths)]
        (recur new-pos (rest pending) new-paths)))))

(defn parse-input [lines]
  lines)

(defn solve-line [line]
  (let [int-line (Integer/parseInt (first (str/split line #"A")))
        line-path (apply list (str/split line #""))
        shortest-paths-for-num-grid (get-shortest-paths-for-sequence
                                     num-grid
                                     [3 2]
                                     line-path
                                     num-grid-symbol-to-pos)
        shortest-paths-for-first-arrow (mapcat #(get-shortest-paths-for-sequence
                                                 arrow-grid
                                                 [0 2]
                                                 %1
                                                 arrow-grid-symbol-to-pos)
                                               shortest-paths-for-num-grid)

        shortest-paths-for-second-arrow (mapcat #(get-shortest-paths-for-sequence
                                                  arrow-grid
                                                  [0 2]
                                                  %1
                                                  arrow-grid-symbol-to-pos)
                                                shortest-paths-for-first-arrow)
        shortest-path (reduce
                       (fn [acc path] (min acc (count path)))
                       Integer/MAX_VALUE
                       shortest-paths-for-second-arrow)]
    ;; (println line)
    ;; (println (first shortest-paths-for-num-grid))
    ;; (println (first shortest-paths-for-first-arrow))
    ;; (println (first shortest-paths-for-second-arrow))
    ;; (println int-line shortest-path (* int-line shortest-path))
    (* int-line shortest-path)))

(defn solve [lines]
  (->> lines
       (map solve-line)
       (reduce +)))

(defn part1
  ([] (part1 "day21/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file-lines)
       (parse-input)
       (solve))))
