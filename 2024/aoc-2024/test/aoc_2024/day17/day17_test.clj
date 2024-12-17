(ns aoc-2024.day17.day17-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day17.day17 :refer :all]))

(deftest day17-part1-ex1-test
      (testing "Day 17 ex1"
        (is (= "4,6,3,5,6,3,5,2,1,0" (part1 "day17/ex1.txt")))))

(deftest basic-ex1 
  (testing "ex1"
    (is (= [{"C" 9 "B" 1} []] (run {"C" 9} [2 6] 0 [])))))

(deftest basic-ex2
  (testing "ex2"
    (is (= [{"A" 10} [0 1 2]] (run {"A" 10} [5 0 5 1 5 4] 0 [])))))


(deftest basic-ex3
  (testing "ex3"
    (is (= [{"A" 0} [4 2 5 6 7 7 7 7 3 1 0]] (run {"A" 2024} [0 1 5 4 3 0] 0 [])))))

(deftest basic-ex4
  (testing "ex4"
    (is (= [{"B" 26} []] (run {"B" 29} [1 7] 0 [])))))

(deftest basic-ex5
  (testing "ex5"
    (is (= [{"B" 44354 "C" 43690} []] (run {"B" 2024 "C" 43690} [4 0] 0 [])))))

(deftest day17-part1-test
      (testing "Day 17 part1"
        (is (= "7,5,4,3,4,5,3,4,6" (part1)))))