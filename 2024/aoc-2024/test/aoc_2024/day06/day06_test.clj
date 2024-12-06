(ns aoc-2024.day06.day06-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day06.day06 :refer :all]))

(deftest day06-part1-ex1-test
      (testing "Day 06 ex1"
        (is (= 41 (part1 "day06/ex1.txt")))))

(deftest day06-part1-test
      (testing "Day 06 part1"
        (is (= 4758 (part1)))))