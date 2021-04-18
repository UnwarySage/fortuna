(ns fortuna.tabs.about)

(defn about-text []
  [:div.column
   [:p.content "Fortuna is an exercise is learning frontend development, ideally while making something the author would find useful. Goals include:"]
   [:ul 
    [:li [:p "Clean, well-tested code"]]
    [:li [:p "Mobile Friendly and Accesible"]]
    [:li [:p "Progessive Web App"]]]])

(defn made-with-text []
  [:div.column
   [:p "Made with:"
    [:ul 
     [:li [:a {:href "https://clojurescript.org/"} "ClojureScript"]]
     [:li [:a {:href "https://shadow-cljs.github.io/docs/UsersGuide.html"} "Shadow-cljs"]]
     [:li [:a {:href "https://bulma.io/"} "Bulma"]]]]])

(defn about-tab []
  [:div
   [:p.title.is-1 "Fortuna"]
   [:p.subtitle-is-3 "A Simple Diceroller"]
   [:hr]
   [:div.columns
    [about-text]
    [made-with-text]]])