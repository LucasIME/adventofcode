(ns aoc-2024.day05.day05-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day05.day05 :refer :all]))

(deftest day05-part1-ex1-test
      (testing "Day 05 ex1"
        (is (= 143 (part1 "day05/ex1.txt")))))

(deftest day05-part1-test
      (testing "Day 05 part1"
        (is (= 5091 (part1)))))

(deftest day05-part2-ex1-test
      (testing "Day 05 part 2ex1"
        (is (= 123 (part2 "day05/ex1.txt")))))

(deftest day05-part2-test
      (testing "Day 05 part2"
        (is (= 123 (part2)))))