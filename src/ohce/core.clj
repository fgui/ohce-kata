(ns ohce.core)

(defn get-hour-now []
  (.get (java.util.Calendar/getInstance) java.util.Calendar/HOUR_OF_DAY))

(defn greeting [hour]
  (cond
    (or (< hour 6) (>= hour 20)) "¡Buenas noches "
    (<= 6 hour 11) "¡Buenos días "
    (<= 12 hour 19) "¡Buenas tardes "
    )
  )

(defn main [& args]

  (println (str (greeting (get-hour-now)) (first args) "!"))
  (loop [line (read-line)]
    (if (= "Stop!" line)
      (println (str "Adios " (first args)))
      (recur (read-line))))
  )
