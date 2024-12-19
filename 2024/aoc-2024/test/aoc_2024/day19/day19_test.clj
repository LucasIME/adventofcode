(ns aoc-2024.day19.day19-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day19.day19 :refer :all]))

(deftest day19-part1-ex1-test
      (testing "Day 19 ex1"
        (is (= 6 (part1 "day19/ex1.txt")))))

(deftest day19-part1-test
      (testing "Day 19 part1"
        (is (= 285 (part1)))))

(deftest day19-part2-ex1-test
      (testing "Day 19 ex1 part 2"
        (is (= 16 (part2 "day19/ex1.txt")))))

(deftest day19-part1-test
      (testing "Day 19 part2"
        (is (= -1 (part2)))))