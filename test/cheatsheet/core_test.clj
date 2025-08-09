(ns cheatsheet.core-test
  (:require [clojure.test :refer [deftest is testing]]
            [cheatsheet.core :refer []]))

(deftest a-test
  (testing "fixed"
    (is (= 1 1))))
