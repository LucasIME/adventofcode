(ns aoc-2024.day20.day20-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day20.day20 :refer :all]
            [aoc-2024.utils.utils :as utils]))

(defn get-cheat-freq [file-name]
  (let [[grid start target-pos] (-> file-name
                                    (utils/read-file-lines)
                                    (parse-input))
        [shortest prev-map] (shortest-path grid start target-pos)
        best-path (get-path prev-map target-pos)
        position-to-target-dist (pos-to-dist best-path)]
    (frequencies (collect-cheats grid best-path position-to-target-dist))))

(deftest day20-part1-ex1-test
  (testing "Day 20 ex1"
    (is (= {2 14
            4 14
            6 2
            8 4
            10 2
            12 3
            20 1
            36 1
            38 1
            40 1
            64 1} (get-cheat-freq "day20/ex1.txt")))))

(deftest day20-part1-test
  (testing "Day 20 part1"
    (is (= 1404 (part1)))))