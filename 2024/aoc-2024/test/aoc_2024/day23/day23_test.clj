(ns aoc-2024.day23.day23-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day23.day23 :refer :all]))

(deftest day23-part1-ex1-test
  (testing "Day 23 ex1"
    (is (= 7 (part1 "day23/ex1.txt")))))

(deftest day23-part1-test
  (testing "Day 23 part1"
    (is (= 1344 (part1)))))
