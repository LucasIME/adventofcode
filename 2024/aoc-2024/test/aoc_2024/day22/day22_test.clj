(ns aoc-2024.day22.day22-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day22.day22 :refer :all]))

(deftest day22-part1-ex1-test
  (testing "Day 22 ex1"
    (is (= 37327623 (part1 "day22/ex1.txt")))))

(deftest ^:slow day22-part1-test
  (testing "Day 22 part1"
    (is (= 14392541715 (part1)))))

(deftest day22-part2-ex2-test
  (testing "Day 22 ex2 part 2"
    (is (= 23 (part2 "day22/ex2.txt")))))

(deftest ^:slow day22-part2-test
  (testing "Day 22 part2"
    (is (= 1628 (part2)))))