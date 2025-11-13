module Day01Spec (spec) where

import Day01 (part1)
import Test.Hspec
import Prelude (($), readFile)

spec :: Spec
spec = describe "Day 01" $ do
    it "solves part 1" $ do
      let path = "./test/resources/day01/day01.txt"
      input <- readFile path
      let result = part1 input
      result `shouldBe` "Sample input content\n"

    it "solves part 2" $ do
      let path = "./test/resources/day01/day01.txt"
      input <- readFile path
      let result = part1 input
      result `shouldBe` "Sample input content 2\n"
