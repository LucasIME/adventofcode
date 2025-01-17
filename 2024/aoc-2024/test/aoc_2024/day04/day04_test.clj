(ns aoc-2024.day04.day04-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day04.day04 :refer :all]))

(deftest day04-part1-ex1-test
      (testing "Day 04 ex1"
        (is (= 18 (part1 "day04/ex1.txt")))))

(deftest day04-part1-test
      (testing "Day 04 part1"
        (is (= 2654 (part1)))))

(deftest day04-part2-ex1-test
      (testing "Day 04 part2 ex1"
        (is (= 9 (part2 "day04/ex1.txt")))))

(deftest day04-part2-test
      (testing "Day 04 part2"
        (is (= 1990 (part2)))))