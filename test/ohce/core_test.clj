(ns ohce.core-test
  (:require [clojure.test :refer :all]
            [ohce.core :as ohce]))

(def in (atom []))
(def out (atom []))
(def hour (atom 0))

(defn mock-read-line []
  (let [l (first @in)]
    (reset! in (rest @in))
    l))

(defn set-inputs! [inputs] (reset! in inputs))

(defn mock-println [s]
  (swap! out conj s))

(defn get-output [] @out)

(defn reset-output [] (reset! out []))

(defn mock-hour-now []
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
   [println mock-println
    read-line mock-read-line
    ohce/hour-now mock-hour-now]

    (testing "test-mock-works"
      (set-inputs! ["hello"])
      (println (read-line))
      (is (= ["hello"] (get-output)))
      (println "world")
      (is (= ["hello" "world"] (get-output))))

    (testing "greetings"
      (testing "Testing hours"
        (test-input-output "Francesc" 20 ["Stop!"] ["¡Buenas noches Francesc!" "Adios Francesc"])
        (test-input-output "Francesc" 21 ["Stop!"] ["¡Buenas noches Francesc!" "Adios Francesc"])
        (test-input-output "Francesc" 5 ["Stop!"] ["¡Buenas noches Francesc!" "Adios Francesc"])
        (test-input-output "Francesc" 0 ["Stop!"] ["¡Buenas noches Francesc!" "Adios Francesc"])
        (test-input-output "Francesc" 6 ["Stop!"] ["¡Buenos días Francesc!" "Adios Francesc"])
        (test-input-output "Francesc" 9 ["Stop!"] ["¡Buenos días Francesc!" "Adios Francesc"])
        (test-input-output "Francesc" 11 ["Stop!"] ["¡Buenos días Francesc!" "Adios Francesc"])
        (test-input-output "Francesc" 12 ["Stop!"] ["¡Buenas tardes Francesc!" "Adios Francesc"])
        (test-input-output "Francesc" 14 ["Stop!"] ["¡Buenas tardes Francesc!" "Adios Francesc"])
        (test-input-output "Francesc" 19 ["Stop!"] ["¡Buenas tardes Francesc!" "Adios Francesc"])))

    (testing "Full test"
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
