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
  (loop [l1 v1, l2 v2, out []]
    (cond
      (empty? l1) out
      :else (recur 
             (rest l1) 
             (rest l2) 
             (into out (concat (repeat (first l1) (- (count v1) (count l1))) 
                               (repeat (first l2) "#") 
                     ))))))

(defn compress [row]
  (loop [pending row, rev (reverse row), out '(), tail-to-ignore 0]
    (cond
      (empty? pending) (reverse out)
      (>= tail-to-ignore (count pending)) (reverse out)
      (= "#" (first rev)) (recur pending (rest rev) out (inc tail-to-ignore))
      (not= "#" (first pending)) (recur (rest pending) 
                                        rev 
                                        (conj out (first pending)) 
                                        tail-to-ignore)
      :else (recur (rest pending)
                   (rest rev)
                   (conj out (first rev))
                   (inc tail-to-ignore)))))

(defn checksum [input]
  (->> input 
       (map-indexed (fn [i x] (* i x )))
       (reduce +)))

(defn solve [[occupied free]]
  (let [combined (combine occupied free)
        compressed (compress combined)
        check (checksum compressed)]
    check))

(defn part1 
  ([] (part1 "day09/input.txt"))
  ([file-name]
  (-> file-name
      (utils/read-file)
      (parse-input)
      (solve))))

(defn solve2 [[occupied free]]
  (let [combined (combine occupied free)
        compressed (compress combined)
        check (checksum compressed)]
    check))

(defn part2 
  ([] (part2 "day09/input.txt"))
  ([file-name]
  (-> file-name
      (utils/read-file)
      (parse-input)
      (solve2))))
