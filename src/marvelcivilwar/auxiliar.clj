(ns marvelcivilwar.auxiliar
  (:require [clojure.data.json :as json]))

(defn increment [value]
  (+ value 1))

(defn reduce [value]
  (- value 1))

(defn is-valid-instruction? [instruction]
  (and (>= 1 instruction) (<= 4 instruction)))

(defn not-available-position [xposition yposition]
 (json/write-str {:errorcode "0x0001" :description (str "The position (" xposition "," yposition ") is not available.") :xpos -1 :ypos -1 :id -1 :type -1 :direction -1}))

(defn not-valid-position [xposition yposition]
  (json/write-str {:errorcode "0x0002" :description (str "The position (" xposition "," yposition ") is not valid.") :xpos -1 :ypos -1 :id -1 :type -1 :direction -1}))

(defn not-valid-type [type]
 (json/write-str {:errorcode "0x0003" :description (str "The type " type " is not valid.") :xpos -1 :ypos -1 :id -1 :type -1 :direction -1}))

(defn not-valid-player [id]
 (json/write-str {:errorcode "0x0004" :description (str "The id " id " is not valid.") :xpos -1 :ypos -1 :id -1 :type -1 :direction -1}))

(defn not-valid-player-type []
  (json/write-str {:errorcode "0x0005" :description "I'm sorry, Dave. I'm afraid I can't do that. #teamIronMan players can neither move or attack." :xpos -1 :ypos -1 :id -1 :type -1 :direction -1}))

(defn missed-shot []
  (json/write-str {:errorcode "0" :description "Nothing happened." :xpos -1 :ypos -1 :id -1 :type -1 :direction -1}))

(defn right-shot [type]
  (if (= 2 type)
    (json/write-str {:errorcode "0" :description "You hit a #teamIronMan player!" :xpos -1 :ypos -1 :id -1 :type -1 :direction -1})
    (json/write-str {:errorcode "0" :description "Friendly fire is not allowed in this game. Please, be careful for the other players' sake" :xpos -1 :ypos -1 :id -1 :type -1 :direction -1})))

