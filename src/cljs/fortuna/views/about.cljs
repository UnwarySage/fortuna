(ns fortuna.views.about
  (:require
    [fortuna.views.common :as common]))

(defn about-panel []
  [:div
   [:div.hero.is-small.is-primary.is-bold.is-hidden-touch
    [:div.hero-body
     [:h1.title "Fortuna"]
     [:h1.subtitle "version 0.1.0"]]]
   [common/tab-bar]
   [:div.container
    [:div.box
     [:div.columns
      [:div.column
       [:h1.title "Fortuna"]
       [:p.flow-text
        "Fortuna is a small experiment, teaching myself front end development, with
        the goals of making something small, usable, and getting it out the door. 
        It's very much alpha software for the foreseeable future, so proceed with caution."]
       [:a {:href "https://github.com/UnwarySage/fortuna/"}
        "Check out the source"]
       [:div.column
        [:h1.title "Made with"]]
       [:ul
         [:li 
          [:a {:href "https://clojurescript.org/"}
           "clojure-script"]
          [:li
           [:a {:href "https://github.com/Day8/re-frame"}
            "re-frame"]]
          [:li
           [:a {:href "https://bulma.io/"}
            "bulma.io"]]]]]]]]])
 
