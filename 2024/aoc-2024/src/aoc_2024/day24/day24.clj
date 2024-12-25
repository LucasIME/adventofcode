(ns aoc-2024.day24.day24
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.string :as str]
            [clojure.core.match :as match]))

(defn parse-nums [raw-nums]
  (let [raw-lines (str/split raw-nums #"\n")
        symbol-and-num-list (map (fn [line]
                                   (let [[symbol raw-n] (str/split line #": ")]
                                     [symbol {:val (Integer/parseInt raw-n)}])) raw-lines)]
    symbol-and-num-list))

(defn parse-gates [raw-gates]
  (let [raw-lines (str/split raw-gates #"\n")
        gates (map (fn [line]
                     (let [[symbol1 op symbol2 _arrow output] (str/split line #" ")]
                       [output {:symbol1 symbol1
                                :op op
                                :symbol2 symbol2}])) raw-lines)]

    gates))

(defn parse-input [raw-input]
  (let [[raw-nums raw-gates] (str/split raw-input #"\n\n")
        symbol-and-num-list (parse-nums raw-nums)
        gates (parse-gates raw-gates)]
    (into {} (concat symbol-and-num-list gates))))


(defn do-op [op val1 val2]
  (case op
    "AND" (bit-and val1 val2)
    "OR" (bit-or val1 val2)
    "XOR" (bit-xor val1 val2)))


(defn get-val [graph entry]
  (match/match [(get graph entry)]
    [nil] nil
    [{:val val}] val
    [{:symbol1 symbol1 :op op :symbol2 symbol2}] (do-op op
                                                        (get-val graph symbol1)
                                                        (get-val graph symbol2))))

(defn bit-to-num [bits]
  (reduce (fn [acc [i bit]] (+ acc (bit-shift-left bit i)))
          0
          (map-indexed (fn [i n] [i n]) bits)))

(defn solve [graph]
  (let [all-keys (keys graph)
        sorted-z-keys (sort (filter #(str/starts-with? %1 "z") all-keys))
        bit-vals (map #(get-val graph %1) sorted-z-keys)
        n (bit-to-num bit-vals)]
    n))

(defn part1
  ([] (part1 "day24/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file)
       (parse-input)
       (solve))))

(defn print-sub-graph [sub-graph]
  (println "subgraph {")
  (print "rank = same;")
  (run! (fn [[key val]] (print " " key ";")) sub-graph)
  (println "}"))

(defn print-graph [graph]
  (println "digraph {")
  (let [z-nodes (filter (fn [[key val]] (str/starts-with? key "z")) graph)
        y-nodes (filter (fn [[key val]] (str/starts-with? key "y")) graph)
        x-nodes (filter (fn [[key val]] (str/starts-with? key "x")) graph)
        ]
        (print-sub-graph (sort z-nodes))
        (print-sub-graph (sort y-nodes))
        (print-sub-graph (sort x-nodes)))
  (run! (fn [[key val]]
          (match/match [val]
            [{:val val2}] (+ 1 1)
            ;; [{:val val2}] (println val2 " -> " key ";")
            [{:symbol1 symbol1 :op op :symbol2 symbol2}] (do 
                                                           (let [op-name (str symbol1 op symbol2)]
                                                           (println symbol1 " -> " op-name ";")
                                                           (println symbol2 " -> " op-name ";")
                                                           (println op-name " -> " key ";")))

            )) graph)
  (println "}"))

(defn set-x-and-y [graph i]
  (reduce (fn [acc index]
            (let [acc-with-x (assoc acc (format "x%02d" index) {:val (if (= i index) 1 0)} )
                  acc-with-y (assoc acc-with-x (format "y%02d" index) {:val (if (= i index) 1 0)} )]
              acc-with-y
              )) graph (range 46)))


(defn swap [graph a b]
  (let [cur-a (get graph a)
        cur-b (get graph b)]
    (assoc (assoc graph b cur-a) a cur-b)))

(defn solve2 [graph]
  (doseq [k (range 46)]
    (let [shift k
        new-graph (set-x-and-y graph shift)
        new-graph2 (swap new-graph "z08" "cdj")
        new-graph3 (swap new-graph2 "z16" "mrb")
        new-graph4 (swap new-graph3 "z32" "gfm")
        new-graph5 (swap new-graph4 "qjd" "dhm")
        x (bit-shift-left 1 shift)
        y (bit-shift-left 1 shift)
        expected (+ x y)
        actual (solve new-graph5)]
      (if (not= actual expected)
        (println "shift: " shift "x: " x "y: " y "expected: " expected "actual: " actual)
        (+ 1 1)))))

(defn part2
  ([] (part2 "day24/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file)
       (parse-input)
       (solve2))))