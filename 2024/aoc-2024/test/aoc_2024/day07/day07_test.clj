(ns aoc-2024.day07.day07-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day07.day07 :refer :all]))

(deftest day07-part1-ex1-test
      (testing "Day 07 ex1"
        (is (= 3749 (part1 "day07/ex1.txt")))))

(deftest day07-part1-test
      (testing "Day 07 part1"
        (is (= 10741443549536 (part1)))))