(ns aoc-2024.day20.day20-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day20.day20 :refer :all]
            [aoc-2024.utils.utils :as utils]))

(defn get-cheat-freq [file-name min radius]
  (let [[grid start target-pos] (-> file-name
                                    (utils/read-file-lines)
                                    (parse-input))
        [shortest prev-map] (shortest-path grid start target-pos)
        best-path (get-path prev-map target-pos)
        position-to-target-dist (pos-to-dist best-path)
        cheats (collect-cheats grid best-path position-to-target-dist radius)
        good-enough-cheats (filter #(>= %1 min) cheats)]
    (frequencies good-enough-cheats)))

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
            64 1} (get-cheat-freq "day20/ex1.txt" 0 2)))))

(deftest day20-part1-test
  (testing "Day 20 part1"
    (is (= 1404 (part1)))))


(deftest day20-part2-ex1-test
  (testing "Day 20 ex1 part 2"
    (is (= {50 32
            52 31
            54 29
            56 39
            58 25
            60 23
            62 20
            64 19
            66 12
            68 14
            70 12
            72 22
            74 4
            76 3} (get-cheat-freq "day20/ex1.txt" 50 20)))))


(deftest ^:slow day20-part2-test
  (testing "Day 20 part2"
    (is (= 1010981 (part2)))))