(ns aoc-2024.day19.day19
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.string :as str]))

(defn parse-input [raw-input]
  (let [[raw-towels raw-entries] (str/split raw-input #"\n\n")
        towels (str/split raw-towels #", ")
        entries (str/split raw-entries #"\n")]
    [towels entries]))

(defn is-valid? [design towels]
  (cond
    (empty? design) true
    :else (not= nil (some #(and 
                            (str/starts-with? design %1)
                            (is-valid? (subs design (count %1)) towels)) 
                          towels))))

(defn solve [[towels designs]]
  (->> designs
       (filter #(is-valid? %1 towels))
       (count)))

(defn part1 
  ([] (part1 "day19/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file)
       (parse-input)
       (solve))))

(defn count-valid-ways [design towels]
  (cond
    (empty? design) 1
    :else (let [possible-starts (filter #(str/starts-with? design %1) towels)]
            (if (empty? possible-starts) 0
                (reduce + (map #(count-valid-ways (subs design (count %1)) 
                                                  towels) 
                               possible-starts))))))

(defn solve2 [[towels designs]]
  (->> designs
       (map #(count-valid-ways %1 towels))
       (reduce +)))

(defn part2 
  ([] (part2 "day19/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file)
       (parse-input)
       (solve2))))