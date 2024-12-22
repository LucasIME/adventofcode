(ns aoc-2024.day22.day22-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day22.day22 :refer :all]))

(deftest day22-part1-ex1-test
  (testing "Day 22 ex1"
    (is (= 37327623 (part1 "day22/ex1.txt")))))

(deftest day22-part1-test
  (testing "Day 22 part1"
    (is (= 14392541715 (part1)))))
