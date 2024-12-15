(ns aoc-2024.day14.day14
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.string :as str]))

(defn parse-robot [line]
  (let [[raw-left raw-right] (str/split line #" v=")
        [px py] (map #(Integer/parseInt %1) (str/split (subs raw-left 2)  #",")) 
        [vx vy] (map #(Integer/parseInt %1) (str/split raw-right #","))]
    {:pos {:x px :y py} :v {:x vx :y vy}}))

(defn parse-input [lines]
  (map parse-robot lines))

(defn pos-rem [x y]
  (rem (+ y (rem x y)) y))

(defn position-after [robot time rows cols]
  (let [raw-x (+ (get-in robot [:pos :x])
                 (* time (get-in robot [:v :x])))
        raw-y (+ (get-in robot [:pos :y])
                 (* time (get-in robot [:v :y])))
        x (pos-rem raw-x cols) 
        y (pos-rem raw-y rows)]
    {:x x :y y}))

(defn quadrant [pos rows cols]
  (let [mid-row (int (/ rows 2))
        mid-col (int (/ cols 2))
        row (get pos :y)
        col (get pos :x)]
    (cond 
      (or (= row mid-row) (= col mid-col)) -1
      (and (< row mid-row) (< col mid-col)) 0
      (and (< row mid-row) (> col mid-col)) 1
      (and (> row mid-row) (< col mid-col)) 2
      (and (> row mid-row) (> col mid-col)) 3)))

(defn solve [robots rows cols]
  (let [positions (map #(position-after %1 100 rows cols) robots)
        pos-by-quadrant (group-by #(quadrant %1 rows cols) positions)
        real-quadrants (filter #(not= (first %1) -1) pos-by-quadrant)
        safety-factor (reduce * (map #(count (second %1)) real-quadrants))]
    safety-factor))

(defn part1 
  ([] (part1 "day14/input.txt" 103 101))
  ([file-name rows cols]
  (-> file-name
      (utils/read-file-lines)
      (parse-input)
      (solve rows cols))))

(defn print-grid [pos-map rows cols]
  (let [grid (for [row (range rows)] 
               (map #(if (contains? pos-map [%1 row]) "#" ".") 
                    (range cols)))
        str-array (map str/join grid)]
    (doseq [line str-array] (println line))))

(defn into-map [positions]
  (reduce (fn [acc pos]
            (assoc acc [(get pos :x) (get pos :y)] 1))  
          {} 
          positions))

(defn count-neighs [pos pos-map]
  (let [row (get pos :y)
        col (get pos :x)]
    (count (for [newrow (range (dec row) (+ 2 row)) 
                 newcol (range (dec col) (+ 2 col)) 
                 :when (and (or (not= row newrow) 
                                (not= col newcol)) 
                            (contains? pos-map [newcol newrow]))] 
             1)))
  
  )

(defn grid-neigh-count [positions]
  (let [pos-map (into-map positions)
        neigh-count-list (map #(count-neighs %1 pos-map) positions)]
    (reduce + neigh-count-list)))


(defn solve2 [robots rows cols]
  (let [all-positions (for [time (range 10000)] 
                        (list time
                        (map #(position-after %1 time rows cols)
                             robots)))
        neigh-count-by-position (map #(list (second %1) (grid-neigh-count (second %1)) (first %1)) 
                                     all-positions)
        [best-pos freq final-time] (apply max-key second neigh-count-by-position)]
    (print-grid (into-map best-pos) rows cols)
    final-time))

(defn part2 
  ([] (part2 "day14/input.txt" 103 101))
  ([file-name rows cols]
  (-> file-name
      (utils/read-file-lines)
      (parse-input)
      (solve2 rows cols))))
