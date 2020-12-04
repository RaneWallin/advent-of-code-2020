(require '[clojure.string :as string])
(def filename "day4.txt")

(def pass-data
  (map
   #(string/split % #"\n|\s")
   (string/split (slurp filename) #"\n\n")))

(def pass-map
  (map 
    (fn
      [row]
      (into {} (map #(assoc {} (keyword (re-find #"^.+(?=:)" %)) (re-find #"(?<=:).+" %)) row))) pass-data))

(defn validate-passport
  [passport]
  (or (= (count passport) 8)
      (and (= (count passport) 7) (nil? (get passport :cid)))))

(defn validate-passports
  []
  (reduce
   (fn
     [total-valid passport]
     (if (validate-passport passport) (inc total-valid) total-valid)) 0 pass-map))

(defn print-passport-results
  []
  (map
   #(println % " " (validate-passport %)) pass-map))
