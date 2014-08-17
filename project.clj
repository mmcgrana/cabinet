(defproject cabinet "0.0.1"
  :description "REST datastore interface."
  :dependencies
    [[ring/ring-jetty-adapter "0.2.5"]
     [ring-json-params "0.1.0"]
     [compojure "0.4.0"]
     [clj-json "0.2.0"]]
  :dev-dependencies
    [[lein-run "1.0.0-SNAPSHOT"]]
  :main cabinet.web)
