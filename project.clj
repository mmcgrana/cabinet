(defproject cabinent "0.0.1"
  :description "JSON datastore interface."
  :dependencies
    [[org.clojure/clojure "1.2.0-RC1"]
     [org.clojure/clojure-contrib "1.2.0-RC1"]
     [ring/ring-core "0.2.5"]
     [ring/ring-devel "0.2.5"]
     [ring/ring-jetty-adapter "0.2.5"]
     [compojure "0.4.0"]
     [hiccup "0.2.6"]
     [clj-json "0.3.0-SNAPSHOT"]
     [ring-json-params "0.1.0-SNAPSHOT"]]
  :dev-dependencies
    [[lein-run "1.0.0-SNAPSHOT"]])
