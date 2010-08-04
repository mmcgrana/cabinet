(ns cabinet.elem
  (:use clojure.contrib.condition)
  (:refer-clojure :exclude (list get delete)))

(def elems (atom {}))

(defn list []
  @elems)

(defn get [id]
  (or (@elems id)
      (raise :type :not-found
             :message (format "elem '%s' not found" id))))

(defn put [id attrs]
  (if (empty? attrs)
    (raise :type :invalid
           :message "attrs are empty")
    (let [new-attrs (merge (@elems id) attrs)]
      (swap! elems assoc id new-attrs)
      new-attrs)))

(defn delete [id]
  (let [old-attrs (get id)]
    (swap! elems dissoc id)
    old-attrs))
