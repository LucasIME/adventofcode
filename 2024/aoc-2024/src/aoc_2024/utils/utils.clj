(ns aoc-2024.utils.utils
  (:require [clojure.java.io :as io]))


(defn read-file [filename]
  (slurp (io/resource filename)))

(defn read-file-lines [filename]
  (with-open [reader (io/reader (io/resource filename))]
    (doall (line-seq reader))))

(defn to-char-matrix [lines]
  (->> lines
       (map #(map str %1))
       (map vec)
       (vec))) 