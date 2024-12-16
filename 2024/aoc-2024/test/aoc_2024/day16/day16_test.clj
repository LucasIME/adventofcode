(ns aoc-2024.day16.day16-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day16.day16 :refer :all]))

(deftest day16-part1-ex1-test
      (testing "Day 16 ex1"
        (is (= 7036 (part1 "day16/ex1.txt")))))

(deftest day16-part1-ex2-test
      (testing "Day 16 ex2"
        (is (= 11048 (part1 "day16/ex2.txt")))))

(deftest day16-part1-test
      (testing "Day 16 part1"
        (is (= 99488 (part1)))))