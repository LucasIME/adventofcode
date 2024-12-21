(ns aoc-2024.day21.day21-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day21.day21 :refer :all]))

(deftest shortest-path-for-num-seq-makes-sense
  (testing "Shortest path for num seq makes sense"
    (is (= (list ["<" "A" "^" "A" "^" "^" ">" "A" "v" "v" "v" "A"]
                 ["<" "A" "^" "A" "^" ">" "^" "A" "v" "v" "v" "A"]
                 ["<" "A" "^" "A" ">" "^" "^" "A" "v" "v" "v" "A"])
           (get-shortest-paths-for-sequence num-grid [3 2] [0 2 9 "A"] num-grid-symbol-to-pos)))))

(deftest day21-part1-ex1-test
  (testing "Day 21 ex1"
    (is (= 126384 (part1 "day20/ex1.txt")))))

(deftest day21-part1-test
  (testing "Day 21 part1"
    (is (= -1 (part1)))))