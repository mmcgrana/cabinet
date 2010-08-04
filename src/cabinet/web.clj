(ns cabinet.web
  (:use compojure.core)
  (:use ring.middleware.reload)
  (:use ring.middleware.json-params)
  (:require [clj-json.core :as json])
  (:require [cabinet.elem :as elem])
  (:import org.codehaus.jackson.JsonParseException)
  (:import clojure.contrib.condition.Condition))

(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json"}
   :body (json/generate-string data)})

(def error-info
  {:attributes-invalid [400 "attributes invalid"]
   :elem-not-found     [404 "elem not found"]})

(defn wrap-error-handling [handler]
  (fn [req]
    (try
      (or (handler req)
          (json-response {"error" "resource not found"} 404))
      (catch JsonParseException e
        (json-response {"error" "malformed json"} 400))
      (catch Condition e
        (let [{:keys [message status]} (meta e)]
          (json-response {"error" message} status))))))

(defn wrap-failsafe [handler]
  (fn [req]
    (try
      (handler req)
      (catch Exception e
        (json-response {"error" "internal server error"} 500)))))

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
  (-> #'handler
    (wrap-json-params)
    (wrap-error-handling)
    (wrap-failsafe)
    (wrap-reload ['cabinet.web 'cabinet.elem])))
