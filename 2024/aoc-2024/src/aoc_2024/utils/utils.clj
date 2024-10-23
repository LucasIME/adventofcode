(ns aoc-2024.utils.utils
  (:require [clojure.java.io :as io]))

(defn read-file [filename]
  (with-open [reader (io/reader (io/resource filename))]
    (doall (line-seq reader))))