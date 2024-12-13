(ns aoc-2024.day13.day13-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day13.day13 :refer :all]))

(deftest day13-part1-ex1-test
      (testing "Day 13 ex1"
        (is (= 480 (part1 "day13/ex1.txt")))))

(deftest day13-part1-test
      (testing "Day 13 part1"
        (is (= 29711 (part1)))))

(deftest day13-part2-test
      (testing "Day 13 part2"
        (is (= 94955433618919 (part2)))))