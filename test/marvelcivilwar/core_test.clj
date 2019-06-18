(ns marvel-civil-war.core-test
  (:require [clojure.test :refer :all]
            [marvel-civil-war.core :refer :all]
            [clojure.string :as strrr]))


(deftest test-app
   (testing "create-player")

  (testing "clear-json"
    (is (= (create-arena) "{\"errorcode\":\"0\",\"description\":\"Arena successfully created!\",\"id\":0,\"type\":0,\"xpos\":0,\"ypos\":0,\"direction\":0}")))
  
  (testing "hello world"
    (is (= (hello-world) "Welcome to Marvel's Civil War Fight!")))
  
  (testing "current-state"
    (is (= (current-state) "{\"errorcode\":\"0\",\"description\":\"Arena is empty!\",\"id\":0,\"type\":0,\"xpos\":0,\"ypos\":0,\"direction\":0}")))
  
  (testing "create-arena"
    (is (= (create-arena) "{\"errorcode\":\"0\",\"description\":\"Arena successfully created!\",\"id\":0,\"type\":0,\"xpos\":0,\"ypos\":0,\"direction\":0}")))
  
  (testing "create-player"
    (is (strrr/includes?  (create-player 1 1 1 1) "\"type\":1,\"xpos\":1,\"ypos\":1,\"direction\":1}"))) 
  
  (testing "create-player-wrong-xposition"
    (is (strrr/includes?  (create-player 1 55 0 1) "{\"errorcode\":\"0x0002\""))) 
  
  (testing "create-player-wrong-yposition"
    (is (strrr/includes?  (create-player 1 0 51 1) "{\"errorcode\":\"0x0002\""))) 
  
  (testing "operate-player"
    (is (strrr/includes?  (operate-player "1234" 5) "{\"errorcode\":\"0x0004\""))) 
  
  
  (testing "clear-json"
    (is (= (create-arena) "{\"errorcode\":\"0\",\"description\":\"Arena successfully created!\",\"id\":0,\"type\":0,\"xpos\":0,\"ypos\":0,\"direction\":0}"))))

