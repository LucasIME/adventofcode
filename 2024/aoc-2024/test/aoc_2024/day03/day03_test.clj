(ns aoc-2024.day03.day03-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day03.day03 :refer :all]))

(deftest day03-part1-ex1-test
      (testing "Day 02 ex1"
        (is (= 161 (part1 "day03/ex1.txt")))))

(deftest day03-part1-test
      (testing "Day 03 part1"
        (is (= 161289189 (part1)))))
