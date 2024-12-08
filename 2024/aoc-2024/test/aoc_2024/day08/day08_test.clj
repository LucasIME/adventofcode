(ns aoc-2024.day08.day08-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day08.day08 :refer :all]))

(deftest day08-part1-ex1-test
      (testing "Day 08 ex1"
        (is (= 14 (part1 "day08/ex1.txt")))))

(deftest day08-part1-test
      (testing "Day 08 part1"
        (is (= -1 (part1)))))