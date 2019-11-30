(ns fortuna.views
  (:require
   [re-frame.core :as re-frame]
   [fortuna.subs :as subs]
   [fortuna.roller :as roll]))
   


;; home
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
             
(defn home-panel []
    [:div
     
     [:div.hero.is-primary.is-bold.is-small.is-hidden-touch
      [:div.hero-body
       [:div
         [:h1.title "Fortuna"]
         [:h1.subtitle "Make your fortune"]]]]
     [tab-bar]
     [:div.container
      [:div
       [:h1.title "A no-muss RPG dice roller"]]]])
  
       

;; about

(defn about-panel []
  [:div
   [tab-bar]
   [:h1 "This is the About Page."]
   [:div
    [:a {:href "#/"}
     "go to Home Page"]]])

;; dice
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
      
     
(defn dice-panel []
  [:div
   [:div.hero.is-primary.is-bold.is-small.is-hidden-touch
    [:div.hero-body
     [:h1.title "Dice"]
     [:h1.subtitle (str "Lucky Number: " (roll/roll-die 6))]]]
   [tab-bar]
   [roll-dialog 1]
   [roll-history]]) 
   

;; main

(defn- panels [panel-name]
  (case panel-name
    :home-panel [home-panel]
    :about-panel [about-panel]
    :dice-panel [dice-panel]
    [:div]))

(defn show-panel [panel-name]
  [panels panel-name])

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [show-panel @active-panel]))
