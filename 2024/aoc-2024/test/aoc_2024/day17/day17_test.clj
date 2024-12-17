(ns aoc-2024.day17.day17-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day17.day17 :refer :all]))

(deftest day17-part1-ex1-test
      (testing "Day 17 ex1"
        (is (= "4,6,3,5,6,3,5,2,1,0" (part1 "day16/ex1.txt")))))

(deftest day17-part1-test
      (testing "Day 17 part1"
        (is (= -1 (part1)))))