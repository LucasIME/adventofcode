(ns aoc-2024.day25.day25-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day25.day25 :refer :all]))

(deftest day25-part1-ex1-test
  (testing "Day 25 ex1"
    (is (= 3 (part1 "day25/ex1.txt")))))

(deftest day25-part1-test
  (testing "Day 25 part1"
    (is (= 3249 (part1)))))
