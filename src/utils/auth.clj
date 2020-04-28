(ns utils.auth
  (:require 
    [clojure.string :as str]
    [clj-http.client :as http]
    [clojure.data.json :as json]
  )
)

(defn default-auth-headers
  [token]
  { 
    "content-type" "application/json"
    "authorization" (str "Bearer " token)
  }
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