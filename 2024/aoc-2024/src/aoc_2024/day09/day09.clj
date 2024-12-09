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

(defn count-first-n [coll target]
  (loop [v coll, out 0]
    (cond 
      (empty? v) out 
      (= (first v) target) (recur (rest v) (inc out)) 
      :else out)))

;; Should return (new-row, not-matched)
(defn compress-tail [row target n] 
  (loop [pending row, out []]
    (cond 
      (empty? pending) [(drop-last n row), (repeat n target)]
      (not= (first pending) "#") (recur (rest pending) (conj out (first pending)))
      ;; first = "#"
      :else (let [slots (count-first-n pending "#")]
              (if (>= slots n)
                [(concat out
                         (repeat n target)
                         (drop n (drop-last n pending))) []]
                (recur (drop slots pending) (concat out (take slots pending)) ))
              )
      )))

(defn compress2 [row]
  (let [[next-row unmatched] (compress-tail row
                                 (last row)
                                 (count-first-n (reverse row) (last row)))]
    (println "pre: " row )
    (println "post: " next-row )
    (println "unmatched: " unmatched "\n")

    (if (= row next-row)
      row
      (concat (compress2 next-row) unmatched))))
              
(defn solve2 [[occupied free]]
  (let [combined (combine occupied free)
        compressed (compress2 combined)
        k (println compressed)
        clean-compressed (map #(if (= %1 "#") 0 %1) compressed)
        k2 (println clean-compressed)
        check (checksum clean-compressed)]
    check))

(defn part2 
  ([] (part2 "day09/input.txt"))
  ([file-name]
  (-> file-name
      (utils/read-file)
      (parse-input)
      (solve2))))

(part2 "day09/ex1.txt")

(compress-tail '(0 0 "#" "#" "#" 1 1 "#" "#" 2 2) 2 4)

(concat [1 2 3] [4 5 6])
