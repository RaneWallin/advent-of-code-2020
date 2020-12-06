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
        ))

(def answer-set
  (map #(reduce
         (fn  [group-set answers]
           (into group-set answers)) #{} %) puzzle-data))

(defn sum-sets
  [answers]
  (reduce
   (fn [total, group-answers]
     (+ total (count group-answers))) 0 answers))

(defn combine-answers
  [answers]
    (->> answers
         (reduce
                (fn [group-vec single-vec]
                  (into group-vec single-vec)) [])
         ))

(defn char-map
  [chars]
  (reduce
   (fn [tmp-map char]
     (let [char-key (keyword char)
           char-count (get tmp-map (keyword char))]
       (if (nil? char-count)
         (into tmp-map {char-key 1})
         (into tmp-map {char-key (inc char-count)}))
       ))
   {} chars))

(defn all-common-answers
  [answers]
  (->> 
   (map #(into [] [(count %) (combine-answers %)]) answers)
   (map #(into [] [(first %) (char-map (nth % 1))]))
   (map #(reduce
          (fn [ans-sum item]
            (if (= (nth item 1) (nth % 0)) 
              (inc ans-sum)
              ans-sum))
          0 (nth % 1)))
   (reduce +)
   ))



