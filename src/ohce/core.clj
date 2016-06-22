(ns ohce.core)

(defn get-hour-now []
  (.get (java.util.Calendar/getInstance) java.util.Calendar/HOUR_OF_DAY))

(defn greeting [name]
  (str
   (let [hour (get-hour-now)]
     (cond
       (or (< hour 6) (>= hour 20)) "¡Buenas noches "
       (<= 6 hour 11) "¡Buenos días "
       (<= 12 hour 19) "¡Buenas tardes "))
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

(defn main [& args]
  (let [name (first args)]
    (print-hello name)
    (loop [line (read-line)]
      (if (exit? line)
        (print-goodbye name)
        (do (print-oche line)
            (recur (read-line)))))))
