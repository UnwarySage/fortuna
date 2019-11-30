(ns fortuna.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [fortuna.events :as events]
   [fortuna.routes :as routes]
   [fortuna.views :as views]
   [fortuna.config :as config]
   ))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn init []
  (routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
