(ns aoc-2024.day23.day23-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day23.day23 :refer :all]))

(deftest day23-part1-ex1-test
  (testing "Day 23 ex1"
    (is (= 7 (part1 "day23/ex1.txt")))))

(deftest day23-part1-test
  (testing "Day 23 part1"
    (is (= 1344 (part1)))))

(deftest day23-part2-ex1-test
  (testing "Day 23 ex1 part 2"
    (is (= "co,de,ka,ta" (part2 "day23/ex1.txt")))))

(deftest ^:slow day23-part2-test
  (testing "Day 23 part2"
    (is (= "ab,al,cq,cr,da,db,dr,fw,ly,mn,od,py,uh" (part2)))))