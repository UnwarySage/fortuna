(ns fortuna.views
  (:require
   [re-frame.core :as re-frame]
   [fortuna.subs :as subs]
   [fortuna.roller :as roll]
   [fortuna.views.about :refer [about-panel]]
   [fortuna.views.common :as common]
   [fortuna.views.rolls :as roll-view]))



;; home


(defn home-panel []
  [:div
   [:div.hero.is-primary.is-bold.is-small.is-hidden-touch
    [:div.hero-body
     [:div
      [:h1.title "Fortuna"]
      [:h1.subtitle "Make your fortune"]]]]
   [common/tab-bar]
   [:div.container
    [:div
     [roll-view/roll-history]]]])

;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    :dice-panel [roll-view/roll-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [show-panel @active-panel]))
