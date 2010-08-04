(ns cabinet.web
  (:use compojure.core)
  (:use ring.middleware.json-params)
  (:require [clj-json.core :as json])
  (:require [cabinet.elem :as elem]))

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

(defroutes handler
  (GET "/elems" []
    (json-response (elem/all)))

  (GET "/elems/:id" [id]
    (json-response (elem/get id)))

  (PUT "/elems/:id" [id attrs]
    (json-response (elem/put id attrs)))

  (DELETE "/elems/:id" [id]
    (json-response (elem/delete id))))

(def app
  (-> handler
    (wrap-json-params)))
