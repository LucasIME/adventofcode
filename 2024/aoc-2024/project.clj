(defproject aoc-2024 "0.1.0-SNAPSHOT"
  :description "Solutions for 2024 Advent of Code"
  :url "https://github.com/lucasime/adventofcode"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.0"]
                 [org.clojure/data.priority-map "1.2.0"]
                 [org.clojure/core.match "1.1.0"]]
  :repl-options {:init-ns aoc-2024.core}
  :profiles {:dev {:test-selectors {:default (complement :slow)
                                    :all (constantly true)
                                    :slow :slow}}})
