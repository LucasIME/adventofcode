(ns aoc-2024.day12.day12-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day12.day12 :refer :all]))

(deftest day12-part1-ex1-test
      (testing "Day 12 ex1"
        (is (= 140 (part1 "day12/ex1.txt")))))

(deftest day12-part1-ex2-test
      (testing "Day 12 ex2"
        (is (= 772 (part1 "day12/ex2.txt")))))

(deftest day12-part1-ex3-test
      (testing "Day 12 ex3"
        (is (= 1930 (part1 "day12/ex3.txt")))))

(deftest day12-part1-test
      (testing "Day 12 part1"
        (is (= 1344578 (part1)))))