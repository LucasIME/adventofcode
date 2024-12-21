(ns aoc-2024.day21.day21-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day21.day21 :refer :all]))

(deftest day21-part1-ex1-test
  (testing "Day 21 ex1"
    (is (= 126384 (part1 "day21/ex1.txt")))))

(deftest day21-part1-test
  (testing "Day 21 part1"
    (is (= 156714 (part1)))))

(deftest day21-part2-test
  (testing "Day 21 part2"
    (is (= 191139369248202 (part2)))))