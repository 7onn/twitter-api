(ns handlers.trendings
  (:require 
    [clojure.string :as str]
    [clj-http.client :as http]
    [clojure.data.json :as json]
  )
  (:use 
    [utils.auth :refer :all]
    [utils.response :refer :all]
  )
)

(def places 
  (json/read-str (slurp "woeid.json"))
)


(println (type places))
; (defn placeByName )

(defn trendings
  [req]
  (def rjtrendings 
    (http/get 
      "https://api.twitter.com/1.1/trends/place.json?id=455825"
      {
        :headers (default-auth-headers (get-bearer-token))
      }
    )
  )
  {
    :status 200
    :body (:body rjtrendings)
    :headers (default-res-headers)
  }
)