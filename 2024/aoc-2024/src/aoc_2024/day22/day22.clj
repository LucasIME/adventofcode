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
