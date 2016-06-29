(ns ohce.core)

(defn hour-now []
  (.get (java.util.Calendar/getInstance) java.util.Calendar/HOUR_OF_DAY))

(defn greeting-hour [hour]
  (cond
    (<= 6 hour 11) "Buenos días"
    (<= 12 hour 19) "Buenas tardes"
    :else "Buenas noches"))

(defn greeting [name]
  (str
   "¡"
   (greeting-hour (hour-now))
   " "
   name
   "!"))

(defn print-hello [name]
  (println (greeting name)))

(defn print-goodbye [name]
  (println (str "Adios " name)))

(defn reverse-string [s]
  (apply str (reverse s)))

(defn print-oche [line]
  (let [rev (reverse-string line)]
    (println rev)
    (when (= line rev) (println "¡Bonita palabra!"))))

(defn exit? [line]
  (= "Stop!" line))

(defn lines []
  (letfn [(rd []
            (let [line (read-line)]
              (if (exit? line)
                nil
                (cons line (lazy-seq (rd))))))]
    (rd)))

(defn main [& [name]]
  (print-hello name)
  (doseq [l (lines)]
    (print-oche l))
  (print-goodbye name))

(defn main-recur [& [name]]
  (print-hello name)
  (loop [line (read-line)]
    (if (exit? line)
      (print-goodbye name)
      (do (print-oche line)
          (recur (read-line))))))
