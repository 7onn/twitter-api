(ns twitter.core
  (:require 
    [org.httpkit.server :as server]
    [compojure.core :refer :all]
    [compojure.route :as route]
    [clojure.string :as str]
    [clojure.data.json :as json]
    [ring.middleware.defaults :refer :all]
    [clj-http.client :as http]
  )
  (:gen-class)
)

(def bearer-token (atom ""))

(defn request-bearer-token
  []
  (def response 
    (http/post 
      "https://api.twitter.com/oauth2/token"
      {
        :basic-auth [
          (System/getenv "TWITTER_API_KEY")
          (System/getenv "TWITTER_API_SECRET")
        ]
        :form-params {:grant_type "client_credentials"}
      }
    )
  )
  (def token (json/read-str (:body response)))
  (reset! bearer-token (get token "access_token"))
  @bearer-token
)

(defn get-bearer-token
  []
  (if (= (str/blank? @bearer-token) true)
        (request-bearer-token)
        @bearer-token
  )
)

(defn default-res-headers
  []
  { 
    "content-type" "application/json"
    "when" (str (java.util.Date.))
  }
)

(defn default-auth-headers
  [token]
  { 
    "content-type" "application/json"
    "authorization" (str "Bearer " token)
  }
)

(defn trendings
  [req]
  (def response 
    (http/get 
      "https://api.twitter.com/1.1/trends/place.json?id=455825"
      {
        :headers (default-auth-headers (get-bearer-token))
      }
    )
  )
  {
    :status 200
    :body (:body response)
    :headers (default-res-headers)
  }
)

(defroutes app-routes
  (GET "/" [] {:status 200 :body "you better post" :headers (default-res-headers)})
  (POST "/trendings" [] trendings)
  (route/not-found "Error, page not found!"))

(def app
  (wrap-defaults app-routes api-defaults)
)

(defn -main
  [& args]
  (let [port (Integer/parseInt (or (System/getenv "PORT") "3000"))]
    (server/run-server app {:port port})
    (println (str "Running webserver at http:/127.0.0.1:" port "/"))
  )
)
