(ns aoc-2024.day12.day12
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.set :as set]))

(defn parse-input [lines]
  (utils/to-char-matrix lines))

(def directions [[1 0] [-1 0] [0 1] [0 -1]])
(def directions-diagonal [[-1 -1] [-1 1] [1 1] [1 -1]])

(defn get-neighs-on-dir [[row col] vectors]
  (map (fn [[dr dc]] [(+ row dr) (+ col dc)]) vectors))


(defn get-neigh-pos [row col]
  (map (fn [[dr dc]] [(+ row dr) (+ col dc)]) directions))

(defn get-valid-neighs [grid row col]
  (filter #(and (not= nil (get-in grid %1))
                (= (get-in grid %1) (get-in grid [row col])))
          (get-neigh-pos row col)))

(defn explore
  ([grid [row col]] (explore grid (list [row col]) #{}))
  ([grid pending visited]
   (cond
     (empty? pending) visited
     (contains? visited (first pending)) (explore grid (rest pending) visited)
     :else (let [[row col] (first pending)
                 valid-neighs (get-valid-neighs grid row col)
                 new-pending (concat valid-neighs (rest pending))
                 visited-with-me (conj visited [row col])]
             (explore grid
                      new-pending
                      visited-with-me)))))

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

(defn solve [grid scoring-fn]
  (let [positions (for [row (range (count grid))
                        col (range (count (get grid row)))]
                    [row col])]
    (loop [to-visit positions, all-visited #{}, score 0]
      (cond
        (empty? to-visit) score
        (contains? all-visited (first to-visit)) (recur (rest to-visit) all-visited score)
        :else (let [island (explore grid (first to-visit))]
                (recur (rest to-visit) (set/union all-visited (set island)) (+ score (scoring-fn grid island))))))))

(defn part1
  ([] (part1 "day12/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file-lines)
       (parse-input)
       (solve island-score))))

(defn expand [[row col]]
  (let [double-row (* 2 row)
        double-col (* 2 col)]
    [[double-row double-col]
     [(inc double-row) double-col]
     [(+ double-row 2) double-col]
     [double-row (inc double-col)]
     [double-row (+ double-col 2)]
     [(inc double-row) (inc double-col)]
     [(inc double-row) (+ double-col 2)]
     [(+ double-row 2) (inc double-col)]
     [(+ double-row 2) (+ double-col 2)]]))

(defn sides [island]
  (let [expanded-island-set (set (mapcat expand island))
        vertice-count (reduce (fn [acc point]
                                (let [all-neigh (filter #(contains? expanded-island-set %)
                                                        (get-neighs-on-dir point (concat directions directions-diagonal)))
                                      diagonal-neigh (filter #(contains? expanded-island-set %)
                                                             (get-neighs-on-dir point directions-diagonal))]
                                  (cond
                                    (or
                                     (= 3 (count all-neigh))
                                     (and
                                      (= 7 (count all-neigh))
                                      (= 3 (count diagonal-neigh)))) (inc acc)
                                    (and
                                     (= 6 (count all-neigh))
                                     (= 2 (count diagonal-neigh))) (+ acc 2)
                                    :else acc)))
                              0 expanded-island-set)]
    vertice-count))

(defn island-score2 [grid island]
  (* (count island)
     (sides island)))

(defn part2
  ([] (part2 "day12/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file-lines)
       (parse-input)
       (solve island-score2))))
