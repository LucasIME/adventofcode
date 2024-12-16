(ns aoc-2024.day16.day16-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day16.day16 :refer :all]))

(deftest day16-part1-ex1-test
      (testing "Day 16 ex1"
        (is (= 11048 (part1 "day16/ex1.txt")))))

(deftest day16-part1-test
      (testing "Day 16 part1"
        (is (= 1495147 (part1)))))