(ns aoc-2024.day22.day22
  (:require [aoc-2024.utils.utils :as utils]))

(defn parse-input [lines]
  (map #(Long/parseLong %1) lines))

(defn mix [n secret]
  (bit-xor n secret))

(defn prune [n]
  (mod n 16777216))

(defn apply-secret [n times]
  (loop [n n,
         times times]
    (cond
      (= 0 times) n
      :else (let [first-secret (-> n
                                   (* 64)
                                   (mix n)
                                   (prune))
                  second-secret (-> first-secret
                                    (Math/floorDiv 32)
                                    (mix first-secret)
                                    (prune))
                  third-secret (-> second-secret
                                   (* 2048)
                                   (mix second-secret)
                                   (prune))]
              (recur third-secret (dec times))))))

(defn solve [initial-secrets n]
  (let [transformed-secrets (map #(apply-secret %1 n) initial-secrets)
        total (reduce + transformed-secrets)]
    total))

(defn part1
  ([] (part1 "day22/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file-lines)
       (parse-input)
       (solve 2000))))

(defn generate-secrets [start times]
  (loop [cur start, n times, out [start]]
    (cond
      (= 1 n) out
      :else (let [new-secret (apply-secret cur 1)]
              (recur new-secret (dec n) (conj out new-secret))))))

(defn differences [coll]
  (let [paired (partition 2 1 coll)]
    (map (fn [[a b]] (- b a)) paired)))

(defn all-seq-and-prices [prices price-changes]
  (let [four-diff-seqs (partition 4 1 price-changes)
        four-diff-and-index (map-indexed (fn [i four-seq] [(vec four-seq) (+ i 4)]) four-diff-seqs)
        seq-to-first-price (reduce (fn [acc [four-seq price-idx]]
                                     (if (contains? acc four-seq) acc
                                         (assoc acc four-seq (nth prices price-idx))))
                                   {} four-diff-and-index)]
    seq-to-first-price))

(defn find-best-seq [best-seq-and-prices]
  (let [all-seqs (reduce (fn [acc k] (conj acc k)) #{} (mapcat keys best-seq-and-prices))
        [best-seq best-sum] (reduce (fn [[best-seq max-so-far] seq]
                                      (let [new-sum (reduce + (map #(get % seq 0) best-seq-and-prices))]
                                        (if (> new-sum max-so-far) [seq new-sum] [best-seq max-so-far])))
                                    [nil Long/MIN_VALUE] all-seqs)]
    best-seq))

(defn solve2 [initial-secrets]
  (let [sequences-of-secrets (map #(generate-secrets %1 2001) initial-secrets)
        sequences-of-prices (vec (map (fn [secrets] (vec (map #(mod % 10) secrets)))
                                      sequences-of-secrets))
        sequences-of-price-changes (vec (map #(vec (differences %1)) sequences-of-prices))
        best-seq-and-prices (for [i (range (count sequences-of-prices))]
                              (all-seq-and-prices (nth sequences-of-prices i)
                                                  (nth sequences-of-price-changes i)))
        best-seq (find-best-seq best-seq-and-prices)
        total (reduce + (map #(get % best-seq 0) best-seq-and-prices))]
    total))

(defn part2
  ([] (part2 "day22/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file-lines)
       (parse-input)
       (solve2))))