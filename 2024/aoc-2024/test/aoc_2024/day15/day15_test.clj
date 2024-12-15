(ns aoc-2024.day15.day15-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day15.day15 :refer :all]))

(deftest day15-part1-ex1-test
      (testing "Day 15 ex1"
        (is (= 10092 (part1 "day15/ex1.txt")))))

(deftest day15-part1-ex2-test
      (testing "Day 15 ex2"
        (is (= 2028 (part1 "day15/ex2.txt")))))

(deftest day15-part1-test
      (testing "Day 15 part1"
        (is (= 1495147 (part1)))))