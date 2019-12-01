(ns fortuna.views.rolls
  (:require
   [re-frame.core :as re-frame]
   [fortuna.subs :as subs]
   [fortuna.views.common :as common]
   [fortuna.roller :as roll]))

(defn roll-dialog
  [roll-id]
  (let [roll-data (re-frame/subscribe [:subs/roll-data roll-id])]
    [:div.box
     [:h1.title (:name @roll-data)]
     [:h1.subtitle (roll/roll-info-string @roll-data)]
     [:h1.subtitle (interpose ", " (:last-roll @roll-data))]
     [:button.button.is-primary {:on-click
                                 (fn [e]
                                   (re-frame/dispatch [:make-roll roll-id]))}
      "Roll"]]))

(defn edit-roll [roll-id]
  (let [roll-data (re-frame/subscribe [:subs/roll-data])]
    [:div
     [:div.panel
      [:div.panel-heading "Edit Dice Roll"]
      [:div.panel-block
       [:div
        [:div.field
         [:label.label "Amount"]
         [:div.control
          [:input.input {:type "number" :placeholder "Number to roll"}]]]
        [:div.field
         [:label.label "Sides"]
         [:div.control
          [:input.input {:type "number" :placeholder "Number of sides"}]]]
        [:div.panel-block
         [:button.button "Save"]]]]]]))

(defn roll-history []
  (let [roll-history (re-frame/subscribe [:subs/roll-history])]
    [:table.table.is-fullwidth
     [:thead
      [:tr
       [:th "Name"]
       [:th "Roll"]
       [:th "Result"]]
      (doall
       (map
        (fn [inp-row]
          [:tr {:key (:id inp-row)}
           [:td (:name inp-row)]
           [:td (roll/roll-info-string inp-row)]
           [:td (interpose ", " (:last-roll inp-row))]])
        (reverse @roll-history)))]]))

(defn roll-panel []
  [:div
   [:div.hero.is-primary.is-bold.is-small.is-hidden-touch
    [:div.hero-body
     [:h1.title "Dice"]
     [:h1.subtitle (str "Lucky Number: " (roll/roll-die 6))]]]
   [common/tab-bar]
   [:div.container
    [roll-dialog 1]
    [roll-history]]])

