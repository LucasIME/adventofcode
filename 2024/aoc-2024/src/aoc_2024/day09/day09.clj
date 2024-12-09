(ns aoc-2024.day09.day09
  (:require [aoc-2024.utils.utils :as utils]))

(defn parse-input [raw_content]
  (let [raw_input (->> raw_content 
                       (seq) 
                       (map #(Integer/parseInt (str %1))))
        partitioned (partition 2 2 (list 0) raw_input)
        occupied (map first partitioned)
        free (map second partitioned)]
    [occupied free]))

(defn combine [v1 v2]
  (loop [l1 v1, l2 v2, out '()]
    (cond
      (empty? l1) (reverse out)
      :else (recur (drop 1 l1) (drop 1 l2) (concat
                                            (repeat (first l2) "#")
                                            (repeat (first l1) (- (count v1) (count l1)))
                                            out)))))

(defn compress2 [row]
  (loop [pending row, rev (reverse row), out '(), tail-to-ignore 0]
    (cond
      (empty? pending) (reverse out)
      (>= tail-to-ignore (count pending)) (reverse out)
      (= "#" (first rev)) (recur pending (drop 1 rev) out (inc tail-to-ignore))
      (not= "#" (first pending)) (recur 
                                  (drop 1 pending) 
                                  rev 
                                  (conj out (first pending))
                                  tail-to-ignore)
      :else (recur (drop 1 pending)
                   (drop 1 rev)
                   (conj out (first rev))
                   (inc tail-to-ignore)))))

(defn checksum [input]
  (->> input 
       (map-indexed (fn [i x] (* i x )))
       (reduce +)))

(defn solve [[occupied free]]
  (println occupied free)
  (let [combined (combine occupied free)
        compressed (compress2 combined)
        check (checksum compressed)]
    (println combined)
    (println compressed)
    check))

(defn part1 
  ([] (part1 "day09/input.txt"))
  ([file-name]
  (-> file-name
      (utils/read-file)
      (parse-input)
      (solve))))
