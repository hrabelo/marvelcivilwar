(ns marvelcivilwar.handler
  (:require [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as json]
            [ring.util.response :refer [response]]
            [ring.middleware.defaults :refer [wrap-defaults site-defaults]]
            [marvelcivilwar.core :refer :all]))

(defroutes app-routes
  
  (GET "/" [] 
    (response (hello-world)))
  
  (GET "/api/currentstate" [] 
    (response (current-state)))
  
  (POST "/api/createarena" {:keys [params]} 
    (response (create-arena)))
  
  (POST "/api/createplayer" {:keys [params]}
    (let [{:keys [type xposition yposition direction]} params] 
      response (create-player type xposition yposition direction)))
  
  (POST "/api/operateplayer" {:keys [params]}
    (let [{:keys [id instruction]} params] 
      response (operate-player id instruction)))
  
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (json/wrap-json-params)
      (json/wrap-json-response)))

