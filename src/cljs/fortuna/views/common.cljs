(ns fortuna.views.common
  (:require
   [re-frame.core :as re-frame]
   [fortuna.subs :as subs]))

(defn tab-bar []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [:div.tabs.is-centered.is-medium
     [:ul
      (doall
       (map (fn [[panel-key display link]]
              [:li {:class
                    (when
                     (= @active-panel panel-key)
                      "is-active")
                    :key panel-key}
               [:a {:href link} display]])
            [[:home-panel "Home" "#/"]
             [:dice-panel "Dice" "#/dice"]
             [:about-panel "About" "#/about"]]))]]))

