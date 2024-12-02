(ns aoc-2024.day02.day02-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day02.day02 :refer :all]))

(deftest day02-ex1-test
      (testing "Day 02 ex1"
        (is (= 2 (part1 "day02/ex1.txt")))))

(deftest day02-part1-test
      (testing "Day 02 part1"
        (is (= 321 (part1)))))