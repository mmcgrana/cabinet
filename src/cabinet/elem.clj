(ns cabinet.elem
  (:use clojure.contrib.condition)
  (:refer-clojure :exclude (get delete)))

(def elems (atom {}))

(defn all []
  @elems)

(defn get [id]
  (or (@elems id)
      (raise :message "elem not found"
             :status 404)))

(defn put [id attrs]
  (if (empty? attrs)
    (raise :message "attrs are empty"
           :status 400)
    (let [new-attrs (merge (get id) attrs)]
      (swap! elems assoc id new-attrs)
      new-attrs)))

(defn delete [id]
  (let [old-attrs (get id)]
    (swap! elems dissoc id)
    old-attrs))
