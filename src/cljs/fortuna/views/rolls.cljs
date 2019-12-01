(ns fortuna.views.rolls
  (:require
   [re-frame.core :as re-frame]
   [reagent.core :as re]
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
  (let [roll-data (re-frame/subscribe [:subs/roll-data roll-id])]
    [:div
     [:div.panel
      [:div.panel-heading (str "Edit " (:name @roll-data))]
      [:div.panel-block
       [:div
        [:div.field
         [:label.label "Amount"]
         [:div.control
          [:input.input {:type "number"
                         :placeholder (str (:amount @roll-data))
                         :on-change (fn [e]
                                      (re-frame/dispatch
                                       [:modify-roll roll-id {:amount (-> e .-target .-value js/parseInt)}]))}]]]
        [:div.field
         [:label.label "Sides"]
         [:div.control
          [:input.input {:type "number"
                         :placeholder (str (:sides @roll-data))
                         :on-change (fn [e]
                                      (re-frame/dispatch
                                       [:modify-roll roll-id {:sides
                                                              (-> e .-target .-value js/parseInt)}]))}]]]]]]]))

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

(defn roll-tile [roll-id]
  (let [roll-data (re-frame/subscribe [:subs/roll-data roll-id])]
    [:div.box.tile.is-child {:on-click (fn [e]
                                         (re-frame/dispatch [:make-roll roll-id]))}
     [:div
      [:h1.title.is-size-4 (:name @roll-data)]
      [:h1.subtitle.is-size-6 (roll/roll-info-string @roll-data)]]]))

(defn roll-panel []
  [:div
   [:div.hero.is-primary.is-bold.is-small.is-hidden-touch
    [:div.hero-body
     [:h1.title "Dice"]
     [:h1.subtitle (str "Lucky Number: " (roll/roll-die 6))]]]
   [common/tab-bar]
   [:div.container
    [edit-roll 1]
    [:div.tile.is-ancestor
     [:div.tile.is-parent
      [roll-tile 1]
      [roll-tile 1]
      [roll-tile 1]
      [roll-tile 1]]]]])


