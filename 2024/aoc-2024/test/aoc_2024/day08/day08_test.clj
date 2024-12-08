(ns aoc-2024.day08.day08-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day08.day08 :refer :all]))

(deftest day08-part1-ex1-test
      (testing "Day 08 ex1"
        (is (= 14 (part1 "day08/ex1.txt")))))

(deftest day08-part1-test
      (testing "Day 08 part1"
        (is (= 341 (part1)))))

(deftest day08-part2-ex1-test
      (testing "Day 08 ex1 part2"
        (is (= 34 (part2 "day08/ex1.txt")))))

(deftest day08-part2-ex2-test
      (testing "Day 08 ex2 part2"
        (is (= 9 (part2 "day08/ex2.txt")))))

(deftest day08-part2-test
      (testing "Day 08 part2"
        (is (= 1134 (part2)))))