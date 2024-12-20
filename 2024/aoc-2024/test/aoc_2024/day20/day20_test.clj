(ns aoc-2024.day20.day20-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day16.day16 :refer :all]))

(deftest day20-part1-ex1-test
  (testing "Day 20 ex1"
    (is (= -1 (part1 "day20/ex1.txt")))))

(deftest day20-part1-test
  (testing "Day 20 part1"
    (is (= -1 (part1)))))