(require '[clojure.string :as string])

(def day6 "day6.txt")

(def question-map
  {:a 0 :b 0 :c 0 :d 0 :e 0 :f 0 :g 0 :h 0 :i 0
    :j 0 :k 0 :l 0 :m 0 :n 0 :o 0 :p 0 :q 0 :r 0
    :s 0 :t 0 :u 0 :v 0 :w 0 :x 0 :y 0 :z 0})

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
       (if (empty? tmp-map)
         (into question-map {char-key 1})
         (into tmp-map {char-key (inc char-count)}))
       ))
   {} chars))

(defn all-common-answers
  [answers]
  (->> 
   (map #(into [] [(count %) (combine-answers %)]) answers)
   ;; (map #(into [] [%1
   ;;                 (reduce (fn  [ans-map ans]
   ;;                           (if (nil? (get ans-map ans))
   ;;                             (into ans-map {ans 1})
   ;;                             (into ans-map
   ;;                                   {ans
   ;;                                    (inc (get ans-map ans))}))
   ;;                           ) {} %2)]))
   (map #(into [] [(first %) (char-map (nth % 1))]))
   (map #(reduce
          (fn [ans-sum item]
            (if (= (nth item 1) (nth % 0)) 
              (inc ans-sum)
              ans-sum))
          0 (nth % 1)))
   (reduce +)
   ))



