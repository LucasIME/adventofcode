(ns aoc-2024.day13.day13
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.string :as str]))

(def a-cost 3)
(def b-cost 1)

(defn parse-button [raw-button]
  (let [[_ raw-right] (str/split raw-button #"X+")
        [raw-x raw-y] (str/split raw-right #", Y+")] 
    {:X (Long/parseLong raw-x) 
     :Y (Long/parseLong raw-y)}))

(defn parse-prize [raw-prize]
  (let [[_ raw-right] (str/split raw-prize #"X=")
        [raw-x raw-y] (str/split raw-right #", Y=")] 
    {:X (Long/parseLong raw-x)
     :Y (Long/parseLong raw-y)}))

(defn parse-prize2 [raw-prize]
  (let [[_ raw-right] (str/split raw-prize #"X=")
        [raw-x raw-y] (str/split raw-right #", Y=")]
    {:X (+ (Long/parseLong raw-x) 10000000000000)
     :Y (+ (Long/parseLong raw-y) 10000000000000)}))

(defn parse-group [raw-group prize-parser]
  (let [[raw-button-a raw-button-b raw-prize] (str/split raw-group #"\n")]
    {:a (parse-button raw-button-a)
     :b (parse-button raw-button-b)
     :prize (prize-parser raw-prize)}))

(defn parse-input [raw_input prize-parser]
  (let [groups (str/split raw_input #"\n\n")]
    (map #(parse-group %1 prize-parser) groups)))

(defn is-int? [x] 
  (= x (long (Math/floor x))))

(defn min-presses [group]
  (let [px (get-in group [:prize :X])
        py (get-in group [:prize :Y])
        v1x (get-in group [:a :X])
        v1y (get-in group [:a :Y])
        v2x (get-in group [:b :X])
        v2y (get-in group [:b :Y])
        b-count (/ (- (* py v1x) (* px v1y)) (- (* v2y v1x) (* v2x v1y)))
        a-count (- (/ px v1x) (/ (* b-count v2x) v1x))]
    (if (and (is-int? a-count)
             (is-int? b-count))
      (+ (* a-cost a-count) (* b-cost b-count))
      Long/MAX_VALUE)))

(defn solve [groups]
  (let [costs (map min-presses groups)
        possible-costs (filter #(not= %1 Long/MAX_VALUE) costs)]
    (reduce + possible-costs)))

(defn part1 
  ([] (part1 "day13/input.txt"))
  ([file-name]
  (-> file-name
      (utils/read-file)
      (parse-input parse-prize)
      (solve))))

(defn part2 
  ([] (part2 "day13/input.txt"))
  ([file-name]
  (-> file-name
      (utils/read-file)
      (parse-input parse-prize2)
      (solve))))
