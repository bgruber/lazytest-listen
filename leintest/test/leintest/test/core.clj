(ns leintest.test.core
  (:use [leintest.core])
  (:use [lazytest.describe :only (describe it)]))

(describe twice "Number doubler"
          (it "can double a positive integer"
              (= 4 (twice 2)))
          (it "can double a negative integer"
              (= -2 (twice -1)))
	  (it "can double a ratio"
	      (= 1 (twice 1/2)))
          (it "can double 0"
              (= 0 (twice 0))))