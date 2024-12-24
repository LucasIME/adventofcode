(ns aoc-2024.day24.day24-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day24.day24 :refer :all]))

(deftest day24-part1-ex1-test
  (testing "Day 24 ex1"
    (is (= 4 (part1 "day24/ex1.txt")))))

(deftest day24-part1-ex2-test
  (testing "Day 24 ex2"
    (is (= 2024 (part1 "day24/ex2.txt")))))

(deftest day24-part1-test
  (testing "Day 24 part1"
    (is (= 55114892239566 (part1)))))
