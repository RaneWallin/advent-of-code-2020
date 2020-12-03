(require '[clojure.string :as string])
(declare read-input split-on-colon
         clean-data build-map get-req-map
         split-on-newline)

(def filename "day2.txt")


(def pw-data
   (clean-data (read-input)))

(defn find-valids
  [pw-list]
  (reduce
   (fn [count cur]
     (if (valid-pw? cur)
       (inc count)
       count)) 0 pw-list))

(defn valid-pw?
  [pw-map]
  (def req-match
    (count-matches
     (get pw-map :pw) (get pw-map :req)))
  (and (<= req-match (get pw-map :max))
       (>= req-match (get pw-map :min))))

(defn clean-data
  [input]
   (map #(build-map %)
        (map #(split-on-colon %)
             (split-on-newline input))))

(defn read-input
  []
  (slurp filename))

(defn split-on-colon
  [input]
  (string/split input #":"))

(defn remove-whitespace
  [input]
  (string/replace input #" " ""))

(defn split-on-newline
  [input]
  (string/split input #"\n"))

(defn build-map
  [[reqs pw]]
  (into
   (into {} (get-req-map reqs))
         {:pw (remove-whitespace pw) })) 

(defn get-req-map
  [input]
  (assoc {} :min (Integer/parseInt
                  (re-find #"^\d+" input))
            :max (Integer/parseInt
                  (re-find #"(?<=-)\d+" input))
            :req (re-find #"[a-zA-Z]+" input)))

(defn print-data
  []
  (println pw-data))

(defn count-matches
  [my-string sub]
  (def matcher
    (re-matcher (re-pattern sub) my-string))
  (loop
      [mtch matcher
       count 0]
    (if (nil? (re-find mtch))
      count
      (recur mtch (inc count))))
  )
