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

(def error-codes
  {:invalid 400
   :not-found 404})

(defn wrap-error-handling [handler]
  (fn [req]
    (try
      (or (handler req)
          (json-response {"error" "resource not found"} 404))
      (catch JsonParseException e
        (json-response {"error" "malformed json"} 400))
      (catch Condition e
        (let [{:keys [type message]} (meta e)]
          (json-response {"error" message} (error-codes type)))))))

(defn wrap-failsafe [handler]
  (fn [req]
    (try
      (handler req)
      (catch Exception e
        (json-response {"error" "internal server error"} 500)))))

; (defroutes handler
;   (GET "/elems" []
;     (json-response (elem/list)))
; 
;   (GET "/elems/:id" [id]
;     (json-response (elem/get id)))
; 
;   (PUT "/elems/:id" [id attrs]
;     (json-response (elem/put id attrs)))
; 
;   (DELETE "/elems/:id" [id]
;     (json-response (elem/delete id))))
;
; (def app
;   (-> #'handler
;     (wrap-json-params)
;     (wrap-error-handling)
;     (wrap-failsafe)
;     (wrap-reload ['cabinet.web 'cabinet.elem])))

(defroutes handler
  (GET "/" []
    (json-response {"hello" "world"}))

  (PUT "/" [name]
    (json-response {"hello" name})))

(def app
  (-> #'handler
    wrap-json-params))
