(ns fortuna.db)

(def default-db
  {:name "re-frame"
   :roll-history []
   :rolls {1 {:name "Damage roll"
              :sides 6
              :amount 4
              :modifier 2
              :last-roll [14 7]}}})
