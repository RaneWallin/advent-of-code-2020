(ns advent-of-code.core-test
  (:require [clojure.test :refer :all]
            [advent-of-code.day5 :refer :all]))

(def test-seats [
  { :seat ["F" "B" "F" "B" "B" "F" "F" "R" "L" "R"]
    :loc '(44 5)
    :id 357}
  { :seat ["B" "F" "F" "F" "B" "B" "F" "R" "R" "R"]
    :loc '(70 7)
    :id 567}
  { :seat ["F" "F" "F" "B" "B" "B" "F" "R" "R" "R"]
    :loc '(14 7)
    :id 119}
  { :seat ["B" "B" "F" "F" "B" "B" "F" "R" "L" "L"]
    :loc '(102 4)
    :id 820}
  ])

(def test-max {
   :seats (list
       ["F" "B" "F" "B" "B" "F" "F" "R" "L" "R"]
       ["B" "F" "F" "F" "B" "B" "F" "R" "R" "R"]
       ["F" "F" "F" "B" "B" "B" "F" "R" "R" "R"]
       ["B" "B" "F" "F" "B" "B" "F" "R" "L" "L"])
   :max-id 820
    })

(deftest test-find-seat
  (every?
    (fn
      [seat]
      (testing
          (find-seat (get seat :seat))
        (is (= (get seat :loc))))
      ) test-seats))

(deftest test-seat-id
  (every?
   (fn [seat]
     (testing
         (get-id (find-seat (get seat :seat)))
       (is (= (get seat :id))))) test-seats))

(deftest test-max-id
  (testing
      (get-max-id (get test-max :seats))
    (is (= (get test-max :max-id))))
  )

