(require '[clojure.string :as string])

(def filename "day3.txt")
(def tree "#")
(def offset 3)

(def tree-vec
  (vec (vec
        (map
         #(string/split % #"")
         (string/split (slurp filename) #"\n")))))

(defn print-tree-map
  []
  (map #(println %) tree-vec)) 

(def width (count (get tree-vec 0)))
(def length (count tree-vec))

(defn get-x
  [y x-offset y-offset]
  (int  (* (/  x-offset y-offset) y)))

(defn convert-x
  [x]
  (- x (* width (int (/ x width)))))

(defn count-trees
  [y-offset
   x-offset]
  (loop
      [trees 0
       y 0]
    (if (> y length)
      trees 
      (recur
       (if (= (get (get tree-vec y) (convert-x (get-x y x-offset y-offset))) tree )
         (inc trees)
         trees)
       (+ y y-offset)))))


