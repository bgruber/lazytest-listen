(ns com.iheardata.lazytest-listen.testmaterial-spec
  (:use com.stuartsierra.lazytest)
  (:use com.iheardata.lazytest-listen.testmaterial))

(describe *ns* "Sample tests"
	  (is (= 10 (twice 5)))
	  (is (= -2 (twice -1)))
	  (is (= 0 (twice 0))))