(ns marvel-civil-war.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [marvel-civil-war.handler :refer :all]
            [clojure.string :as strrr]))

(deftest test-app
  (testing "main route"
    (let [response (app (mock/request :get "/"))]
      (is (= (:status response) 200))
      (is (= (:body response) "Welcome to Marvel's Civil War Fight!"))))
  
  (testing "create arena"
    (let [response (app (mock/request :post "/api/createarena"))]
      (is (= (:status response) 200))
      (is (= (:body response) "{\"errorcode\":\"0\",\"description\":\"Arena successfully created!\",\"id\":0,\"type\":0,\"xpos\":0,\"ypos\":0,\"direction\":0}"))))  
  
  (testing "current state"
    (let [response (app (mock/request :get "/api/currentstate"))]
      (is (= (:status response) 200))
      (is (= (:body response) "{\"errorcode\":\"0\",\"description\":\"Arena is empty!\",\"id\":0,\"type\":0,\"xpos\":0,\"ypos\":0,\"direction\":0}"))))  

  (testing "create player"
    (let [response (app (-> (mock/request :post "/api/createplayer") (mock/json-body {:type 1 :xposition 15 :yposition 15 :direction 1})))]
      (is (= (:status response) 200))
      (is (strrr/includes? (:body response) "\"type\":1,\"xpos\":15,\"ypos\":15,\"direction\":1}"))))
  
  (testing "operate player"
    (let [response (app (-> (mock/request :post "/api/operateplayer") (mock/json-body {:id "1234" :instruction 1})))]
      (is (= (:status response) 200))
      (is (strrr/includes? (:body response) "The id 1234 is not valid."))))
  
  (testing "clear json"
    (let [response (app (mock/request :post "/api/createarena"))])))

