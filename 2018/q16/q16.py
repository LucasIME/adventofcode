import sys
from dataclasses import dataclass
from typing import List

class Computer:
    def __init__(self, registers = [0 for i in range(4)]):
        self.registers = [x for x in registers]
        self.methods = [self.addr, self.addi,
                        self.mulr, self.muli,
                        self.banr, self.bani,
                        self.borr, self.bori,
                        self.setr, self.seti,
                        self.gtir, self.gtri, self.gtrr,
                        self.eqir, self.eqri, self.eqrr]

    def reset(self, registers_state):
        self.registers = [x for x in registers_state]

    def addr(self, a, b, c):
        self.registers[c] = self.registers[a] + self.registers[b]
    def addi(self, a, b, c):
        self.registers[c] = self.registers[a] + b 

    def mulr(self, a, b, c):
        self.registers[c] = self.registers[a] * self.registers[b]
    def muli(self, a, b, c):
        self.registers[c] = self.registers[a] * b 

    def banr(self, a, b, c):
        self.registers[c] = self.registers[a] & self.registers[b]
    def bani(self, a, b, c):
        self.registers[c] = self.registers[a] & b 

    def borr(self, a, b, c):
        self.registers[c] = self.registers[a] | self.registers[b]
    def bori(self, a, b, c):
        self.registers[c] = self.registers[a] | b 

    def setr(self, a, _b, c):
        self.registers[c] = self.registers[a]
    def seti(self, a, _b, c):
        self.registers[c] = a

    def gtir(self, a, b, c):
        val = 1 if a > self.registers[b] else 0
        self.registers[c] = val
    def gtri(self, a, b, c):
        val = 1 if self.registers[a] > b else 0
        self.registers[c] = val
    def gtrr(self, a, b, c):
        val = 1 if self.registers[a] > self.registers[b] else 0
        self.registers[c] = val

    def eqir(self, a, b, c):
        val = 1 if a == self.registers[b] else 0
        self.registers[c] = val
    def eqri(self, a, b, c):
        val = 1 if self.registers[a] == b else 0
        self.registers[c] = val
    def eqrr(self, a, b, c):
        val = 1 if self.registers[a] == self.registers[b] else 0
        self.registers[c] = val

@dataclass
class Example:
    before : List[int]
    instruction: List[int]
    after: List[int]

def parse_example(raw_input):
    raw_before, raw_instructions, raw_after = raw_input.split("\n")

    before = [int(x) for x in raw_before[9:-1].split(", ")]
    instructions = [int(x) for x in raw_instructions.split(" ")]
    after = [int(x) for x in raw_after[9:-1].split(", ")]

    return Example(before, instructions, after)

def parse_input():
    raw_input = sys.stdin.read()
    raw_examples, raw_tests = raw_input.split("\n\n\n")

    raw_each_ex = raw_examples.split("\n\n")

    return [parse_example(ex) for ex in raw_each_ex]

def count_matches(example):
    resp = 0
    computer = Computer(example.before)

    _, a, b, c = example.instruction
    for method in computer.methods:
        method(a, b, c)
        if computer.registers == example.after:
            resp += 1
        method(a, b, c)
        computer.reset(example.before)


    return resp

def process(examples):
    matches = 0

    for ex in examples:
        n_match = count_matches(ex)
        if n_match >= 3:
            matches += 1

    return matches

def main():
    entry = parse_input()
    resp = process(entry)
    print(resp)

if __name__ == '__main__':
    main()

