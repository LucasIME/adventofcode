(ns aoc-2024.day09.day09-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day09.day09 :refer :all]))

(deftest day09-part1-ex1-test
      (testing "Day 09 ex1"
        (is (= 1928 (part1 "day09/ex1.txt")))))

;; (deftest ^:slow day09-part1-test
;;       (testing "Day 09 part1"
;;         (is (= 6242766523059 (part1)))))

(deftest day09-part2-ex1-test
      (testing "Day 09 ex1 part2"
        (is (= 2858 (part2 "day09/ex1.txt")))))

;; (deftest ^:slow day09-part2-test
;;       (testing "Day 09 part2"
;;         (is (= -1 (part2)))))