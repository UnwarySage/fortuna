(ns fortuna.tabs.roll-edit
  (:require
   [re-frame.core :as re-frame]
   [fortuna.subs :as subs]
   [fortuna.events :as evs]))

(defn parse-display-area [roll-id]
  (let [parse-structure (re-frame/subscribe [::subs/expression-structure roll-id])
        parse-status (re-frame/subscribe [::subs/expression-structure-status roll-id])]
    [:div
     (if @parse-status
       [:code (str @parse-structure)]
       [:code "Couldn't understand that, sorry"])]))

(defn roll-list-entry [roll-id]
  (let [roll-name (re-frame/subscribe [::subs/roll-name roll-id])
        roll-expression (re-frame/subscribe [::subs/roll-expression roll-id])
        roll-status (re-frame/subscribe [::subs/expression-structure-status roll-id])]
    [:tr {:key roll-id}
     [:td [:p @roll-name]]
     [:td [:p {:class (when-not @roll-status
                        "has-text-warning")} @roll-expression]]
     [:td [:a {:on-click (fn [e]
                           (.preventDefault e)
                           (re-frame/dispatch [::evs/perform-roll roll-id]))} "Roll"]]
     [:td [:a {:on-click (fn [e]
                           (.preventDefault e)
                           (re-frame/dispatch [::evs/edit-roll roll-id]))} "Edit"]]]))

(defn roll-list
  "displays a list of row-entries"
  []
  (let [roll-ids (re-frame/subscribe [::subs/sorted-roll-ids])]
    [:div.roll-list
     [:table.table.is-fullwidth
      [:tbody
       (doall
        (for [present-id @roll-ids]
          [roll-list-entry present-id]))]]
     [:button.button {:on-click (fn [e]
                                  (.preventDefault e)
                                  (re-frame/dispatch [::evs/create-roll]))} "New Roll"]]))


(defn roll-edit-area [roll-id]
  (let [roll-expression (re-frame/subscribe [::subs/roll-expression roll-id])
        roll-name (re-frame/subscribe [::subs/roll-name roll-id])
        roll-expression-status (re-frame/subscribe [::subs/expression-structure-status roll-id])]
    [:div.panel
     [:p.panel-heading @roll-name]
     [:div.panel-block
      [:input.input {:type "text"
                     :value (str @roll-name)
                     :on-change (fn [new-val]
                                  (re-frame/dispatch [::evs/change-roll-name roll-id (-> new-val .-target .-value)]))}]]
     [:div.panel-block
      [:input.input
       {:type "text"
        :value (str @roll-expression)
        :class (if @roll-expression-status
                 "is-primary" "is-warning")
        :on-change (fn [new-val]
                     (re-frame/dispatch [::evs/change-expression roll-id (-> new-val .-target .-value)]))}]]
     [:div.panel-block
      [:button.button.is-outlined.is-fullwidth
       {:on-click (fn [e] (.preventDefault e))}
       "Roll"]]]))


(defn roll-edit-tab []
  [:div.columns
   [:div.column
    [roll-edit-area]]
   [:div.column
    [roll-list]]])