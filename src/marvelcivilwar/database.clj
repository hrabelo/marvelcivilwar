(ns  marvelcivilwar.database
  (:require [clojure.string :as strrr]
            [clojure.data.json :as json]
            [clojure.java.io :as io]))

(import '[java.io RandomAccessFile])

(def jsonfile "src/marvelcivilwar/database.json")

;; Get all items
(defn all-players []
  (if-not (strrr/blank? (slurp jsonfile)) 
    (json/read-str (slurp jsonfile) :key-fn keyword)
    (json/write-str {:errorcode "0" :description "Arena is empty!" :id 0 :type 0 :xpos 0 :ypos 0 :direction 0})))

(defn get-identifiers []
  (if-not (strrr/blank? (slurp jsonfile))
    (map :id (json/read-str (slurp jsonfile) :key-fn keyword))))

(defn get-types []
  (map :type (json/read-str (slurp jsonfile) :key-fn keyword)))

(defn get-directions []
  (map :direction (json/read-str (slurp jsonfile) :key-fn keyword)))

(defn get-all-coordinates []
  (if-not (strrr/blank? (slurp jsonfile))
    (map list (map :xpos (json/read-str (slurp jsonfile) :key-fn keyword)) (map :ypos (json/read-str (slurp jsonfile) :key-fn keyword)))))


;; Get specific items
(defn x-current-coordinate [id]
  (nth (nth (get-all-coordinates) (.indexOf (get-identifiers) id)) 0))

(defn y-current-coordinate [id]
  (nth (nth (get-all-coordinates) (.indexOf (get-identifiers) id)) 1))

(defn current-type [id]
  (nth (get-types) (.indexOf (get-identifiers) id)))

(defn current-direction [id]
  (nth (get-directions) (.indexOf (get-identifiers) id)))

(defn get-id-by-coordinates [xposition yposition]
  (nth (get-identifiers) (.indexOf (get-all-coordinates) [xposition yposition])))

;; Operate json file
(defn clear-json-file []
  (spit jsonfile (.replace (slurp jsonfile) (slurp jsonfile) ""))
  (json/write-str {:errorcode "0" :description "Arena successfully created!" :id 0 :type 0 :xpos 0 :ypos 0 :direction 0}))


(defn append-json-list [jsontext]
  (let [raf (RandomAccessFile. jsonfile "rw")
        lock (.lock (.getChannel raf))    ;; avoid concurrent invocation across processes
        current-length (.length raf)]
    (if (= current-length 0)
      (do
        (.writeBytes raf "[\n")           ;; On the first write, prepend a "["
        (.writeBytes raf jsontext)        ;; ...before the data...
        (.writeBytes raf "\n]\n"))        ;; ...and a final "\n]\n"
      (do
        (.seek raf (- current-length 3))  ;; move to before the last "\n]\n"
        (.writeBytes raf ",\n")           ;; put a comma where that "\n" used to be
        (.writeBytes raf jsontext)        ;; ...then the new data...
        (.writeBytes raf "\n]\n")))       ;; ...then a new "\n]\n"
    (.close lock)
    (.close raf)))


(defn update-json-registry [id line newline]   
  (spit jsonfile (.replace (slurp jsonfile) line newline))
  (json/write-str {:errorcode "0" :description "" :id id :type (current-type id) :xpos (x-current-coordinate id) :ypos (y-current-coordinate id) :direction (current-direction id)}))

(defn insert-player [id type xpos ypos direction]
  (append-json-list (json/write-str {:id id :type type :xpos xpos :ypos ypos :direction direction}))
  (json/write-str {:errorcode "0" :description "" :id id :type type :xpos xpos :ypos ypos :direction direction}))

(defn gets-hit-player [xposition yposition])
  


