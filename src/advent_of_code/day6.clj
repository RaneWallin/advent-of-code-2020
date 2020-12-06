(require '[clojure.string :as string])

(def day6 "day6.txt")

(defn split [regex s]
  (string/split s regex))

(def input (slurp day6))

(def puzzle-data
  (->>  input
        (split #"\n\n")
        (map #(split #"\n|\s" %))
        (map #(map (fn [x] (split #"" x)) %))
        (map #(reduce
               (fn  [group-set answers]
                 (into group-set answers)) #{} %))
        ))

(defn sum-answers
  [answers]
  (reduce
   (fn [total, group-answers]
     (+ total (count group-answers))) 0 answers))

