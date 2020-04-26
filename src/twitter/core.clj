(ns twitter.core
  (:require 
    [org.httpkit.server :as server]
    [compojure.core :refer :all]
    [compojure.route :as route]
    [clojure.string :as str]
    [clojure.data.json :as json]
    [ring.middleware.defaults :refer :all]
  )
  (:gen-class)
)

(defn default-res-headers
  []
  { 
    "content-type" "application/json"
    "when" (str (java.util.Date.))
  }
)

(defroutes app-routes
  (GET "/" [] {:status 200 :body "you better post" :headers (default-res-headers)})
  ; (POST "/webhook" [] webhook)
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
