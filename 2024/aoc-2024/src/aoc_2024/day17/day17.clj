(ns aoc-2024.day17.day17
  (:require [aoc-2024.utils.utils :as utils]
            [clojure.string :as str]))

(defn parse-register [raw-line]
  (let [[_ raw-name-and-val] (str/split raw-line #"Register ")
        [name raw-val] (str/split raw-name-and-val #": ")]
    [name (Integer/parseInt raw-val)]))

(defn parse-registers [raw-registers]
  (let [lines (str/split raw-registers #"\n")
        registers (map parse-register lines)]
    (into {} registers)))

(defn parse-input [raw-input]
  (let [[raw-registers raw-program] (str/split raw-input #"\n\n")
        [_ raw-p-list] (str/split raw-program #"Program: ")
        registers (parse-registers raw-registers)
        p-list (map #(Integer/parseInt %1) (str/split raw-p-list #","))]
    [registers (vec p-list)]))

(defn combo [registers param]
  (cond
    (and (>= param 0) (<= param 3)) param
    (= param 4) (get registers "A")
    (= param 5) (get registers "B")
    (= param 6) (get registers "C")))

(defn adv [registers param ip output]
  (let [numerator (get registers "A")
        denominator (long (Math/pow 2 (combo registers param)))
        result (Math/floorDiv numerator denominator)
        updated-registers (assoc registers "A" result)]
    [updated-registers (+ ip 2) output]))

(defn bxl [registers param ip output]
  (let [xor (bit-xor (get registers "B") param)
        updated-registers (assoc registers "B" xor)]
    [updated-registers (+ ip 2) output]))

(defn bst [registers param ip output]
  (let [result (rem (combo registers param) 8)
        updated-registers (assoc registers "B" result)]
    [updated-registers (+ ip 2) output]))

(defn jnz [registers param ip output]
  (if (= (get registers "A") 0)
    [registers (+ ip 2) output]
    [registers param output]))

(defn bxc [registers param ip output]
  (let [xor (bit-xor (get registers "B") (get registers "C"))
        updated-registers (assoc registers "B" xor)]
    [updated-registers (+ ip 2) output]))

(defn out [registers param ip output]
  (let [val (rem (combo registers param) 8)]
    [registers (+ ip 2) (conj output val)]))

(defn bdv [registers param ip output]
  (let [numerator (get registers "A")
        denominator (long (Math/pow 2 (combo registers param)))
        result (Math/floorDiv numerator denominator)
        updated-registers (assoc registers "B" result)]
    [updated-registers (+ ip 2) output]))

(defn cdv [registers param ip output]
  (let [numerator (get registers "A")
        denominator (long (Math/pow 2 (combo registers param)))
        result (Math/floorDiv numerator denominator)
        updated-registers (assoc registers "C" result)]
    [updated-registers (+ ip 2) output]))


(defn process-op-code [registers op-code param ip output]
  (cond 
    (= op-code 0) (adv registers param ip output)
    (= op-code 1) (bxl registers param ip output)
    (= op-code 2) (bst registers param ip output)
    (= op-code 3) (jnz registers param ip output)
    (= op-code 4) (bxc registers param ip output)
    (= op-code 5) (out registers param ip output)
    (= op-code 6) (bdv registers param ip output)
    (= op-code 7) (cdv registers param ip output)))

(defn run [registers program ip output]
  (loop [registers registers, program program, ip ip, output output]
      (if (>= ip (count program))
      [registers output]
        (let [op (nth program ip) 
              param (nth program (inc ip)) 
              [new-registers new-ip new-out] (process-op-code registers op param ip output)] 
          (recur new-registers program new-ip new-out)))))

(defn solve [[registers program]]
  (let [[new-state output] (run registers program 0 [])]
    (str/join "," output)))

(defn part1 
  ([] (part1 "day17/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file)
       (parse-input)
       (solve))))

(defn solve2 [[registers program]]
  (loop [registers registers, program program, a-val 0]
    (let [updated-registers (assoc registers "A" a-val)
          [new-state output] (run updated-registers program 0 [])]
      (if (= output program)
        a-val
        (recur registers program (inc a-val))))))

(defn part2 
  ([] (part2 "day17/input.txt"))
  ([file-name]
   (-> file-name
       (utils/read-file)
       (parse-input)
       (solve2))))