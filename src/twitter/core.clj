(ns twitter.core
  (:require 
    [org.httpkit.server :as server]
    [compojure.core :refer :all]
    [compojure.route :as route]
    [clojure.string :as str]
    [ring.middleware.defaults :refer :all]
    [clj-http.client :as http]
  )
  (:use 
    [utils.auth :refer :all]
    [utils.response :refer :all]
    [handlers.trendings :refer :all]
  )
  (:gen-class)
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
