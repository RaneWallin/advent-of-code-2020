(ns advent-of-code.day5
  (:require [clojure.string :as string]))

(def my-rows 127)
(def my-seats 7)
(def filename "day5.txt")

(def seat-data
  (map #(string/split % #"")
       (string/split (slurp filename) #"\n")))

(defn get-rounded-midpoint
  [min-row max-row where?]
  (let [mid-point (float (/ (+ min-row max-row) 2))]
    (if (or (= where? "F") (= where? "L"))
      (int (Math/floor mid-point))
      (int (Math/ceil mid-point)))))

(defn get-keepers
  [[min-val max-val]
   where?]
  (if (= (- max-val min-val) 1)
    (if (or (= where? "F") (= where? "L"))
      (list min-val)
      (list max-val))
    (let
        [min-val min-val
         max-val max-val
         mid-point (get-rounded-midpoint min-val max-val where?)]
      (if (or  (= where? "F") (= where? "L"))
        (list min-val mid-point)
        (list mid-point max-val))
      )))

(defn find-seat
  [seat-data]
  (loop
      [row-range (list 0 my-rows)
       seat-range (list 0 my-seats)
       seat-data seat-data]
   (if (empty? seat-data)
     (list (nth row-range 0) (nth seat-range 0))
     (let
         [where? (first seat-data)]
       (recur 
        (if (or (= where? "F") (= where? "B"))
          (get-keepers row-range where?)
          row-range)
       (if (or (= where? "L") (= where? "R"))
         (get-keepers seat-range where?)
         seat-range)
       (rest seat-data))
       ))))

(defn get-id
  [[row seat]]
  (+ (* row 8) seat))

(defn get-max-id
  [in-seats]
  (reduce
   (fn 
     [max-id
      current-row]
     (let [id (get-id (find-seat current-row))]
       (if (> id max-id)
         id
         max-id))) 0 in-seats))

(defn get-seat-ids
  [in-seats]
  (map #(get-id (find-seat %)) in-seats))

(defn find-my-seat
  []
  (loop [seats (sort  (map #(get-id (find-seat %)) seat-data))
         my-seat nil
         last-seat nil]
      (if (nil? last-seat)
        (recur (rest seats) my-seat (first seats))
        (if (not (= (- (nth seats 0) last-seat) 1))
          (- (nth seats 0) 1)
          (recur (rest seats) my-seat (first seats))))))

;; (defn find-my-seat
;;   [in-seats]
;;   ())



