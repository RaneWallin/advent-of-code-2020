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
     (into {} (map
               #(assoc {}
                       (keyword (re-find #"^.+(?=:)" %))
                       (re-find #"(?<=:).+" %))
               row)))
   pass-data))

(defn screen-passport
  [passport]
  (or (= (count passport) 8)
      (and (= (count passport) 7)
           (nil? (get passport :cid)))))

(defn validate-range-inclusive
  [x min max]
  (and (>= x min) (<= x max)))

(defn validate-byr
  [byr]
  (validate-range-inclusive byr 1920 2002))

(defn validate-iyr
  [iyr]
  (validate-range-inclusive iyr 2010 2020))

(defn validate-eyr
  [eyr]
  (validate-range-inclusive eyr 2020 2030))

(defn validate-fields
  [passport]
  (and
   (validate-byr (Integer/parseInt (get passport :byr)))
   (validate-iyr (Integer/parseInt (get passport :iyr)))
   (validate-eyr (Integer/parseInt (get passport :eyr)))
   (let [hgt (re-find #"(\d+)(cm|in)" (get passport :hgt))]
     (if (not (nil? hgt)) 
       (let [val (Integer/parseInt (nth hgt 1))
             units (nth hgt 2)]
         (if (= units "cm")
           (validate-range-inclusive val 150 193)
           (validate-range-inclusive val 59 76)))))
   (let [hcl (re-find #"#[0-9a-f]{6}" (get passport :hcl))]
     (and (not (nil? hcl)) (= hcl (get passport :hcl))))
   (re-find #"amb|blu|brn|gry|grn|hzl|oth" (get passport :ecl))
   (let [pid (get passport :pid)]
     (if (= (count pid) 9)
       (= pid (re-find #"^\d+" pid))
       ))
   ))

(defn validate-passports
  []
  (reduce
   (fn
     [total-valid passport]
     (if (screen-passport passport)
       (if (validate-fields passport)
         (inc total-valid)
         total-valid)
       total-valid))
   0
   pass-map))

(defn print-passport-results
  []
  (map
   #(println % " " (validate-fields %)) pass-map))

