(require '[clojure.string :as string])
(declare has-val?)

(def filename "input.txt")


(def report
  (vec
   (map #(Integer/parseInt %) 
    (string/split  (slurp filename) #"\n")))
  )

(defn printReport
  []
  (println report))

(defn find-matching-vals
  [vals total]
  (loop
      [[cur & tail] vals
       result ()]
    (if (not (empty? result))
      result 
      (if (has-val (- total cur) tail)
        (recur tail (list cur (- total cur)))
        (if (empty? tail) nil  (recur tail ()))
       ))
     )
  )

(defn find-two
  [vals total]
  (find-matching-vals vals total))

(defn find-three
  [vals total]
  (loop
      [[cur & tail] vals
       result ()]
    (if (not (empty? result))
      result
      (if (nil? (find-two tail (- total cur)))
        (recur tail result)
        (recur tail
               (cons cur (find-two tail (- total cur))))
        ))))



(defn get-mult
  [vals]
  (reduce * vals))

(defn has-val?
  [search coll]
  (some #(= search %) coll))

