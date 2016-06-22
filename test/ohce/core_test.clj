(ns ohce.core-test
  (:require [clojure.test :refer :all]
            [ohce.core :refer :all]))

(def in (atom []))
(def out (atom []))

(defn mock-println [s]
  (swap! out conj s))

(defn mock-read-line []
  (let [l (first @in)]
    (reset! in (rest @in))
    l))

(defn get-output [] @out)

(defn reset-output [] (reset! out []))

(defn set-inputs! [inputs] (reset! in inputs))

(deftest a-ohce
  (with-redefs
    [println mock-println
     read-line mock-read-line]

    (testing "test-mock-works"
      (set-inputs! ["hello"])
      (println (read-line))
      (is (= ["hello"] (get-output)))
      (println "world")
      (is (= ["hello" "world"] (get-output))))))
