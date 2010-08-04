(use 'ring.adapter.jetty)
(require '[cabinet.web :as web])

(run-jetty #'web/app {:port 8080})
