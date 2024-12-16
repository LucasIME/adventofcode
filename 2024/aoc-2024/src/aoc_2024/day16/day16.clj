(ns aoc-2024.day16.day16
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.data.priority-map :refer [priority-map]]))

(defn pq-pop [pq]
  (dissoc pq (key (first pq))))

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
    [char-matrix {:pos start-pos :dir 1} end-pos]))

(defn get-spinning-neigh [state]
  (let [cur-dir (:dir state)
        other-dirs (->> directions
                        (map-indexed vector)
                        (filter (fn [[idx item]] (not= item (nth directions cur-dir)) ))
                        (map #(first %1)))]
        
    (map (fn [dir] {:pos (:pos state) :dir dir}) other-dirs)))

(map-indexed vector [5 6 7])

(defn add-pos [[row1 col1] [row2 col2]]
  [(+ row1 row2) (+ col1 col2)])

(defn get-direct-neigh [grid {pos :pos dir :dir}]
  (let [dir-vec (nth directions dir)
        new-pos (add-pos pos dir-vec)
        new-pos-val (get-in grid new-pos)]
    (if (not= new-pos-val "#") 
      {:pos new-pos :dir dir}
      nil)))
  

(defn update-queue-and-dist [pq distances neighs-and-cost]
  (let [to-insert (filter (fn [[state d]] (or (not (contains? distances state))
                              (< d (get distances state)))) 
                          neighs-and-cost)
        new-pq (reduce (fn [acc [pos cost]] (assoc acc pos cost)) 
                       pq 
                       to-insert)
        new-distances (reduce (fn [acc [pos cost]] (assoc acc pos cost)) 
                              distances 
                              to-insert)]
    [new-pq new-distances]))

(defn dikjstra [grid state target-pos]
  (loop [pq (priority-map state 0) , distances {state 0}]
    (cond 
      (empty? pq) distances
      (= (first (first pq)) target-pos) distances
      :else (let [[cur dist] (first pq)
                  rest-pq (pq-pop pq)
                  maybe-direct-neigh (get-direct-neigh grid cur)
                  direction-neighs (get-spinning-neigh cur)
                  direction-neighs-with-cost (map #(vector %1 (+ dist 1000)) direction-neighs)
                  maybe-direct-with-cost (if (nil? maybe-direct-neigh) [] [[maybe-direct-neigh (inc dist)]])
                  neighs-and-cost (concat direction-neighs-with-cost maybe-direct-with-cost)
                  [new-q new-dist] (update-queue-and-dist rest-pq distances neighs-and-cost)]
              (recur new-q new-dist)))))

(defn solve [[grid state target-pos]]
  (let [distances (dikjstra grid state target-pos)
        pos-and-distance (map (fn [[state d]] [ (:pos state) d] ) distances)
        target-distances (filter (fn [[pos d]] (= pos target-pos)) pos-and-distance)
        min-d (reduce (fn [acc [pos d]] (min d acc)) Integer/MAX_VALUE target-distances)]
    min-d))

(defn part1 
  ([] (part1 "day16/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file-lines)
       (parse-input)
       (solve))))
