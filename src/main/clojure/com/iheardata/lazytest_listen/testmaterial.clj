(ns com.iheardata.lazytest-listen.testmaterial
  {:spec com.iheardata.lazytest-listen.testmaterial-spec})

(defn add [x y]
  (if (>= 0 x) y
      (recur (dec x) (inc y))))

(defn twice [x]
  (+ x x))