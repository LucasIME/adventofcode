(ns aoc-2024.day05.day05-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day05.day05 :refer :all]))

(deftest day05-part1-ex1-test
      (testing "Day 05 ex1"
        (is (= 143 (part1 "day05/ex1.txt")))))

(deftest day05-part1-test
      (testing "Day 05 part1"
        (is (= 2654 (part1)))))