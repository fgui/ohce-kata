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

(defn main [& args]
  (let [name (first args)]
    (println (greeting name))
    (loop [line (read-line)]
      (if (= "Stop!" line)
        (println (str "Adios " name))
        (do
          (let [rev (apply str (reverse line))]
            (println rev)
            (when (= line rev) (println "¡Bonita palabra!")))
          (recur (read-line)))))))
