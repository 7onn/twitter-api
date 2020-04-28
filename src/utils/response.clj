(ns utils.response
  (:require 
    [clojure.string :as str]
  )
)

(defn default-res-headers
  []
  { 
    "content-type" "application/json"
    "when" (str (java.util.Date.))
  }
)