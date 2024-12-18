(ns aoc-2024.day18.day18-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day18.day18 :refer :all]))

(deftest day18-part1-ex1-test
      (testing "Day 18 ex1"
        (is (= 22 (part1 "day18/ex1.txt" 7 7 12)))))

;; (deftest day18-part1-test
;;       (testing "Day 18 part1"
;;         (is (= -1 (part1)))))
