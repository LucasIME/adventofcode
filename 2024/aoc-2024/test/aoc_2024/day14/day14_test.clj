(ns aoc-2024.day14.day14-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day14.day14 :refer :all]))

(deftest day14-part1-ex1-test
      (testing "Day 14 ex1"
        (is (= 12 (part1 "day14/ex1.txt" 7 11)))))

(deftest day14-part1-test
      (testing "Day 14 part1"
        (is (= 219150360 (part1)))))
