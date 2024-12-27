(ns aoc-2024.day15.day15
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.string :as str]))

(def dir-map {"<" [0 -1] "^" [-1 0] ">" [0 1] "v" [1 0]})

(defn get-start-pos [grid]
  (for [row (range (count grid))
        col (range (count (get grid row)))
        :when (= (get-in grid [row col]) "@")] 
    [row col]))

(defn clean-moves [raw-moves]
  (->> raw-moves
       (seq)
       (map str)
       (filter #(not= %1 "\n"))))

(defn parse-input [raw-input]
  (let [[raw-grid raw-moves] (str/split raw-input #"\n\n")
        grid (utils/to-char-matrix (str/split raw-grid #"\n"))
        [start-pos] (get-start-pos grid)
        clean-grid (assoc-in grid start-pos ".")]
    [clean-grid (clean-moves raw-moves) start-pos]))

(defn sum-gps [grid]
  (reduce + (for [row (range (count grid))
                  col (range (count (get grid row)))
                  :when (or 
                         (= "O" (get-in grid [row col]))
                         (= "[" (get-in grid [row col])))]
              (+ (* 100 row) col))))

(defn pos-after-dir [[row col] dir]
  (let [[dr dc] (get dir-map dir)]
    [(+ row dr) (+ col dc)]))

(defn can-go? [grid direction pos]
  (let [next-pos (pos-after-dir pos direction)
        next-val (get-in grid next-pos)]
    (cond 
      (= next-val "#") false
      (= next-val ".") true
      :else (can-go? grid direction next-pos))))

(defn move [grid direction initial-pos]
  (loop [grid grid, dir direction, pos initial-pos, last "."]
    (cond 
      (and (not= pos initial-pos)
      (= (get-in grid pos) ".")) (assoc-in grid pos last)
      :else (let [next-pos (pos-after-dir pos dir)]
              (recur (assoc-in grid pos last) dir next-pos (get-in grid pos))))))

(defn next-grid [grid direction pos]
  (if (can-go? grid direction pos)
    [(move grid direction pos) (pos-after-dir pos direction)]
    [grid pos]))

(defn print-grid [grid]
  (doseq [row grid] (println (str/join row))))

(defn find-final-grid [grid moves start-pos]
  (loop [grid grid, moves moves, pos start-pos]
    (cond
      (empty? moves) grid 
      :else (let [[new-grid new-pos] (next-grid grid (first moves) pos)]
              (recur new-grid (rest moves) new-pos)))))

(defn solve [[grid moves start-pos]]
  (let [final-grid (find-final-grid grid moves start-pos)]
    (sum-gps final-grid)))

(defn part1 
  ([] (part1 "day15/input.txt"))
  ([file-name ]
  (-> file-name
      (utils/read-file)
      (parse-input)
      (solve))))

(defn scale-row [row]
  (vec (apply concat 
         (map (fn [c] 
                (cond 
                  (= c "#") ["#" "#"]
                  (= c "O") ["[" "]"]
                  (= c ".") ["." "."]
                  (= c "@") ["@" "."])) 
              row))))

(defn scale-up [grid]
 (vec (map scale-row grid)))

(defn parse-input2 [raw-input]
  (let [[raw-grid raw-moves] (str/split raw-input #"\n\n")
        grid (utils/to-char-matrix (str/split raw-grid #"\n"))
        scaled-grid (scale-up grid)
        [start-pos] (get-start-pos scaled-grid)
        clean-grid (assoc-in scaled-grid start-pos ".")]
    [clean-grid (clean-moves raw-moves) start-pos]))

(defn get-matching-pos [grid [row col]]
  (let [cur-val (get-in grid [row col])]
    (cond 
      (= cur-val "[") [row (inc col)]
      (= cur-val "]") [row (dec col)])))

(defn can-go2? [grid direction start-pos]
  (cond
    (= direction "<") (can-go? grid direction start-pos)
    (= direction ">") (can-go? grid direction start-pos)
    :else (loop [q (conj (clojure.lang.PersistentQueue/EMPTY) 
                         [start-pos direction "."]), 
                 visited #{}]
            (let [ [cur-pos cur-dir last-val] (peek q)
                  cur-val (get-in grid cur-pos)]
              (cond
                (= cur-val "#") false
                (empty? q) true
                (nil? cur-pos) (recur (pop q) visited)
                (and (= cur-val ".")
                     (not= cur-pos start-pos)) (recur (pop q) visited)
                (contains? visited cur-pos) (recur (pop q)
                                                   visited)
                :else (let [new-visited (conj visited cur-pos)
                            next-pos (pos-after-dir cur-pos cur-dir)
                            matching-pos (get-matching-pos grid cur-pos)
                            new-q (-> q
                                       (pop)
                                       (conj [next-pos cur-dir cur-val])
                                      (conj [matching-pos cur-dir "."]))]
                        (recur new-q new-visited)))))))


(defn move2 [grid direction start-pos]
  (cond
    (= direction "<") (move grid direction start-pos)
    (= direction ">") (move grid direction start-pos)
    :else (loop [q (conj (clojure.lang.PersistentQueue/EMPTY) 
                         [start-pos direction "."]), 
                 visited #{},
                 grid grid]
            (let [ [cur-pos cur-dir last-val] (peek q)
                  cur-val (get-in grid cur-pos)]
              (cond
                (empty? q) grid
                (nil? cur-pos) (recur (pop q) visited grid)
                (and (= cur-val ".")
                     (not= cur-pos start-pos)) (recur (pop q) 
                                       visited
                                       (assoc-in grid cur-pos last-val))
                (contains? visited cur-pos) (recur (pop q)
                                                   visited
                                                   grid)
                :else (let [new-visited (conj visited cur-pos)
                            next-pos (pos-after-dir cur-pos cur-dir)
                            matching-pos (get-matching-pos grid cur-pos)
                            new-q (-> q
                                       (pop)
                                       (conj [next-pos cur-dir cur-val])
                                      (conj [matching-pos cur-dir "."]))
                            new-grid (assoc-in grid cur-pos last-val)]
                        (recur new-q new-visited new-grid)))))))

(defn next-grid2 [grid direction pos]
  (if (can-go2? grid direction pos)
    [(move2 grid direction pos) (pos-after-dir pos direction)]
    [grid pos]))

(defn find-final-grid2 [grid moves start-pos]
  (loop [grid grid, moves moves, pos start-pos]
    (cond
      (empty? moves) grid 
      :else (let [[new-grid new-pos] (next-grid2 grid (first moves) pos)]
              (recur new-grid (rest moves) new-pos)))))

(defn solve2 [[grid moves start-pos]]
  (let [final-grid (find-final-grid2 grid moves start-pos)]
    (sum-gps final-grid)))

(defn part2 
  ([] (part2 "day15/input.txt"))
  ([file-name]
  (-> file-name
      (utils/read-file)
      (parse-input2)
      (solve2))))
