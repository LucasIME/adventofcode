(ns aoc-2024.day01.day01-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day01.day01 :refer :all]))

(deftest day01-ex1-test
      (testing "Day 01 ex1"
        (is (= 11 (part1 "day01/ex1.txt")))))

(deftest day01-part1-test
      (testing "Day 01 part1"
        (is (= 2815556 (part1)))))

(deftest day01-ex1-part2-test
      (testing "Day 01 ex1 part2"
        (is (= 31 (part2 "day01/ex1.txt")))))

(deftest day01-part2-test
      (testing "Day 01 part2"
        (is (= 23927637 (part2)))))