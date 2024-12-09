(ns aoc-2024.day09.day09
  (:require [aoc-2024.utils.utils :as utils]))

(defn parse-input [raw_content]
  (let [raw_input (->> raw_content 
                       (seq) 
                       (map #(Integer/parseInt (str %1))))
        partitioned (partition 2 raw_input)
        occupied (map first partitioned)
        free (map second partitioned)]
    [occupied free]))

(defn solve [input]
  input)

(defn part1 
  ([] (part1 "day09/input.txt"))
  ([file-name]
  (-> file-name
      (utils/read-file)
      (parse-input)
      (solve))))
