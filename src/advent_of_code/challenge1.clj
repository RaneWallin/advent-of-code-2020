(require '[clojure.string :as string])
(declare has-val?)

(def filename "input.txt")

; Read file in split on newline to create a vector
; and convert strings to ints
(def report
  (vec
   (map #(Integer/parseInt %) 
    (string/split  (slurp filename) #"\n")))
  )

; View read and converted report
(defn print-report
  []
  (println report))


(defn find-two
  "Accepts a seq of `vals` and finds two values that sum to `total`"
  [vals total]
  (loop
      [[cur & tail] vals
       result {}]
      (if (empty? tail)
        nil
        (if (nil? (get result (- total cur) nil))
          (recur tail (assoc result cur (- total cur)))
          (list (- total  cur) cur)))
      ))
  
  

  (defn find-three
    "Accepts a seq of `vals` and finds three values that sum to `total`
   using [[find-two]]"
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
  "Takes a seq `vals` and returns the product"
  [vals]
  (reduce * vals))

(defn has-val?
  "Searches a collection `col` to see if it contains `search`"
  [search coll]
  (some #(= search %) coll))

