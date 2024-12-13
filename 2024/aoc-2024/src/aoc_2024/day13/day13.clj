(ns aoc-2024.day13.day13
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.string :as str]))

(def max-presses 100)
(def a-cost 3)
(def b-cost 1)

(defn parse-button [raw-button]
  (let [[_ raw-right] (str/split raw-button #"X+")
        [raw-x raw-y] (str/split raw-right #", Y+")
        ]
    {:X (Integer/parseInt raw-x)
     :Y (Integer/parseInt raw-y)}))

(defn parse-prize [raw-prize]
  (let [[_ raw-right] (str/split raw-prize #"X=")
        [raw-x raw-y] (str/split raw-right #", Y=")
        ]
    {:X (Integer/parseInt raw-x)
     :Y (Integer/parseInt raw-y)}))

(defn parse-group [raw-group]
  (let [[raw-button-a raw-button-b raw-prize] (str/split raw-group #"\n")]
    {:a (parse-button raw-button-a)
     :b (parse-button raw-button-b)
     :prize (parse-prize raw-prize)}))

(defn parse-input [raw_input]
  (let [groups (str/split raw_input #"\n\n")]
    (map parse-group groups)))

(defn min-presses [group]
  (let [all-possibilities (for [a-count (range (inc max-presses)) 
                                b-count (range (inc max-presses))]
                            (let [new-x (+ (* a-count (get-in group [:a :X]))
                                           (* b-count (get-in group [:b :X])))
                                  new-y (+ (* a-count (get-in group [:a :Y]))
                                           (* b-count (get-in group [:b :Y])))
                                  cost (+ (* a-cost a-count) (* b-cost b-count))
                                  ]
                              {:is-win? (and (= new-x (get-in group [:prize :X]))
                                             (= new-y (get-in group [:prize :Y])))
                               :cost cost
                               }))
        winning-states (filter #(get %1 :is-win?) all-possibilities)
        winning-costs (map #(get %1 :cost) winning-states)
        ]
    (if (empty? winning-costs) Integer/MAX_VALUE (apply min winning-costs))
    ))

(defn solve [groups]
  (let [costs (map min-presses groups)
        possible-costs (filter #(not= %1 Integer/MAX_VALUE) costs)
        ]
    (reduce + possible-costs)))

(defn part1 
  ([] (part1 "day13/input.txt"))
  ([file-name]
  (-> file-name
      (utils/read-file)
      (parse-input)
      (solve))))