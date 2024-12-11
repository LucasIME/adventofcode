(ns aoc-2024.day11.day11-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day11.day11 :refer :all]))

(deftest day11-part1-ex1-test
      (testing "Day 11 ex1"
        (is (= 22 (part1 "day11/ex2.txt" 6)))))

(deftest day11-part1-ex1-2-test
      (testing "Day 11 ex1 2"
        (is (= 55312 (part1 "day11/ex2.txt" 25)))))

(deftest day11-part1-test
      (testing "Day 11 part1"
        (is (= 172484 (part1)))))
