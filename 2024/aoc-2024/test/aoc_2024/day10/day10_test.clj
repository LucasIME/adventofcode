(ns aoc-2024.day10.day10-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day10.day10 :refer :all]))

(deftest day10-part1-ex1-test
      (testing "Day 10 ex1"
        (is (= 36 (part1 "day10/ex5.txt")))))

(deftest day10-part1-test
      (testing "Day 10 part1"
        (is (= 624 (part1)))))

(deftest day10-part2-ex1-test
      (testing "Day 10 part2 ex1"
        (is (= 81 (part2 "day10/ex5.txt")))))

(deftest day10-part2-test
      (testing "Day 10 part2"
        (is (= 1483 (part2)))))
