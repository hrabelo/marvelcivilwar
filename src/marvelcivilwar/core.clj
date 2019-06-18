 (ns marvelcivilwar.core
   (:require [clojure.data.json :as json]
             [marvelcivilwar.validate :refer :all]
             [marvelcivilwar.database :refer :all]
             [marvelcivilwar.auxiliar :refer :all])) 

(defn hello-world[]
  "Welcome to Marvel's Civil War Fight!")

(defn current-state []
  (all-players))

(defn create-arena []
  (clear-json-file))

(defn create-player [type xposition yposition direction]
  
  (if (is-valid-type? type)
    (if (is-valid-position? xposition yposition)
      (if (is-vacancy? xposition yposition (get-all-coordinates))
        
        (insert-player (str (java.util.UUID/randomUUID)) type xposition yposition direction)
        
        (not-available-position xposition yposition))
      (not-valid-position xposition yposition))
    (not-valid-type type)))


(defn operate-player [id instruction]
  (if (is-valid-player? id (get-identifiers))
    (if (= 1 (current-type id))
      (cond 

        ; Move up
        (= 1 instruction) 
        (if (is-valid-position? (x-current-coordinate id)  (increment (y-current-coordinate id)))
          (if (is-vacancy? (x-current-coordinate id) (increment (y-current-coordinate id)) (get-all-coordinates))
            (update-json-registry 
              id
              (str "\"xpos\":" (x-current-coordinate id) ",\"ypos\":" (y-current-coordinate id) ",\"direction\":" (current-direction id)) 
              (str "\"xpos\":" (x-current-coordinate id) ",\"ypos\":" (increment (y-current-coordinate id)) ",\"direction\":" instruction))
            (not-available-position (x-current-coordinate id)  (increment (y-current-coordinate id)))) (not-valid-position (x-current-coordinate id)  (increment (y-current-coordinate id))))
        
        ; Move down
        (= 2 instruction)
        (if (is-valid-position? (x-current-coordinate id)  (reduce (y-current-coordinate id)))
          (if (is-vacancy? (x-current-coordinate id) (reduce (y-current-coordinate id)) (get-all-coordinates))
            (update-json-registry 
              id
              (str "\"xpos\":" (x-current-coordinate id) ",\"ypos\":" (y-current-coordinate id) ",\"direction\":" (current-direction id)) 
              (str "\"xpos\":" (x-current-coordinate id) ",\"ypos\":" (reduce (y-current-coordinate id)) ",\"direction\":" instruction)) 
            (not-available-position (x-current-coordinate id)  (reduce (y-current-coordinate id)))) (not-valid-position (x-current-coordinate id)  (reduce (y-current-coordinate id))))
        
        ; Move right
        (= 3 instruction) 
        (if (is-valid-position? (increment (x-current-coordinate id))  (y-current-coordinate id))
          (if (is-vacancy? (increment (x-current-coordinate id)) (y-current-coordinate id) (get-all-coordinates))
            (update-json-registry 
              id
              (str "\"xpos\":" (x-current-coordinate id) ",\"ypos\":" (y-current-coordinate id) ",\"direction\":" (current-direction id)) 
              (str "\"xpos\":" (increment (x-current-coordinate id)) ",\"ypos\":" (y-current-coordinate id) ",\"direction\":" instruction))
            (not-available-position (increment (x-current-coordinate id)) (y-current-coordinate id))) (not-valid-position (increment (x-current-coordinate id))  (y-current-coordinate id)))
        
        ; Move left
        (= 4 instruction) 
        (if (is-valid-position? (reduce (x-current-coordinate id))  (y-current-coordinate id))
          (if (is-vacancy? (reduce (x-current-coordinate id)) (y-current-coordinate id) (get-all-coordinates))
            (update-json-registry 
              id
              (str "\"xpos\":" (x-current-coordinate id) ",\"ypos\":" (y-current-coordinate id) ",\"direction\":" (current-direction id)) 
              (str "\"xpos\":" (reduce (x-current-coordinate id)) ",\"ypos\":" (y-current-coordinate id) ",\"direction\":" instruction))
            (not-available-position (reduce (x-current-coordinate id)) (y-current-coordinate id))) (not-valid-position (reduce (x-current-coordinate id))  (y-current-coordinate id)))
        
        ; Attack
        (= 5 instruction)
        (cond
          ; North
          (= 1 (current-direction id))
          (if (is-vacancy? (x-current-coordinate id) (increment (y-current-coordinate id)) (get-all-coordinates)) (missed-shot) (right-shot (current-type (get-id-by-coordinates (x-current-coordinate id) (increment (y-current-coordinate id))))))
          
          ; South
          (= 2 (current-direction id))
          (if (is-vacancy? (x-current-coordinate id) (reduce (y-current-coordinate id)) (get-all-coordinates)) (missed-shot) (right-shot (current-type (get-id-by-coordinates (x-current-coordinate id) (reduce (y-current-coordinate id))))))
          
          ; East
          (= 3 (current-direction id))
          (if (is-vacancy? (increment (x-current-coordinate id)) (y-current-coordinate id) (get-all-coordinates)) (missed-shot) (right-shot (current-type (get-id-by-coordinates (increment (x-current-coordinate id)) (y-current-coordinate id)))))
          
          ;West
          (= 4 (current-direction id))
          (if (is-vacancy? (reduce (x-current-coordinate id))  (y-current-coordinate id) (get-all-coordinates)) (missed-shot) (right-shot (current-type (get-id-by-coordinates  (reduce (x-current-coordinate id))  (y-current-coordinate id)))))))
      (not-valid-player-type))
    (not-valid-player id)))
  
