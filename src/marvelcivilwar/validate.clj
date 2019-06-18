(ns marvelcivilwar.validate)

(defn is-valid-type? [type]
  (or (= type 1) (= type 2)))

(defn is-valid-position? [xposition yposition]
  (and (<= xposition 50) (<= yposition 50) (>= xposition 0) (>= yposition 0)))

(defn is-vacancy? [xposition yposition allcoordinates]
  (empty? (filter (fn [x] (and (= xposition (nth x 0))(= yposition (nth x 1)))) allcoordinates)))

(defn is-valid-player? [id allidentifiers]
  (if-not (empty? allidentifiers) (nat-int? (.indexOf allidentifiers id))))

