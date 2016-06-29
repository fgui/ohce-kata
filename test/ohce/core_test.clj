(ns ohce.core-test
  (:require [clojure.test :refer :all]
            [ohce.core :as ohce]))

(def in (atom []))
(def out (atom []))
(def hour (atom 0))

(defn stub-read-line []
  (let [l (first @in)]
    (reset! in (rest @in))
    l))

(defn set-inputs! [inputs] (reset! in inputs))

(defn stub-println [s]
  (swap! out conj s))

(defn get-output [] @out)

(defn reset-output [] (reset! out []))

(defn stub-hour-now []
  @hour)

(defn set-hour-now [h] (reset! hour h))

(defn test-input-output [arg hour inputs outputs]
  (reset-output)
  (set-hour-now hour)
  (set-inputs! inputs)
  (ohce/main arg)
  (is (= outputs (get-output))))

(deftest test-ohce
  (with-redefs
   [println stub-println
    read-line stub-read-line
    ohce/hour-now stub-hour-now]

    (testing "test stubs for test"
      (reset-output)
      (set-inputs! ["hello"])
      (println (read-line))
      (is (= ["hello"] (get-output)))
      (println "world")
      (is (= ["hello" "world"] (get-output))))

    (testing "greetings depend on hour e2e"
      (testing "Testing hours"
        (letfn [(test-greeting [hours greet]
                  (doseq [hour hours]
                    (test-input-output
                     "Name" hour ["Stop!"] [(str "¡" greet " Name!") "Adios Name"]))
                  )]
          (test-greeting [20 21 4 5] "Buenas noches")
          (test-greeting [6 9 11] "Buenos días")
          (test-greeting [12 14 19] "Buenas tardes"))))

    (testing "end 2 end test given sample"
      (test-input-output "Pedro" 20
                         ["hola"
                          "oto"
                          "stop"
                          "Stop!"]
                         ["¡Buenas noches Pedro!"
                          "aloh"
                          "oto"
                          "¡Bonita palabra!"
                          "pots"
                          "Adios Pedro"]))))
