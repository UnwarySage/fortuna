(ns fortuna.views
  (:require
   [re-frame.core :as re-frame]
   [fortuna.subs :as subs]
   [fortuna.events :as evs]
   [fortuna.tabs.roll-edit :as roll-tab]
   [fortuna.tabs.about :as about-tab]
   [fortuna.tabs.roll-history :as history-tab]))


(defn about-tab []
  [:div
   [:h1.title "About"]])


(defn error-tab []
  [:div
   [:h1.title.has-text-danger "Error"]
   [:h1.subtitle
    " You found a tab that doesn't exist"]])

(def ui-mode-to-tabs
  {:roll-tab {:display-fn roll-tab/roll-edit-tab
              :name "Rolls"}
   :about-tab {:display-fn about-tab/about-tab
               :name "About"}
   :history-tab {:display-fn history-tab/roll-history-tab
                 :name "History"}})

(defn tab-bar-entry [tab-key]
  (let [ui-tab (re-frame/subscribe [::subs/change-ui-tab])]
    [:li {:class (when (= tab-key @ui-tab) "is-active")}
     [:a {:on-click (fn [e]
                      (.preventDefault e)
                      (re-frame/dispatch [::evs/change-ui-tab tab-key]))}
      (:name (get ui-mode-to-tabs tab-key))]]))

(defn tab-bar []
  [:div.tabs
   [:ul
    [tab-bar-entry :roll-tab]
    [tab-bar-entry :history-tab]
    [tab-bar-entry :about-tab]]])


(defn main-layout []
  (let [ui-tab (re-frame/subscribe [::subs/change-ui-tab])]
    [:div
     [tab-bar]
     [:div.container
      [(:display-fn (get ui-mode-to-tabs @ui-tab))]]]))


(defn main-panel []
  [:div
   [main-layout]])